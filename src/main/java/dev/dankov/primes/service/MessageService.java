package dev.dankov.primes.service;

import dev.dankov.primes.dao.ChatRepository;
import dev.dankov.primes.dao.MessageRepository;
import dev.dankov.primes.dao.UserRepository;
import dev.dankov.primes.dto.request.CreateMessageRequestDto;
import dev.dankov.primes.dto.response.MessageResponseDto;
import dev.dankov.primes.dto.response.MessageStatusResponseDto;
import dev.dankov.primes.entity.ChatEntity;
import dev.dankov.primes.entity.MessageEntity;
import dev.dankov.primes.entity.UserEntity;
import dev.dankov.primes.enums.MessageStatus;
import dev.dankov.primes.exception.EntityNotFoundException;
import dev.dankov.primes.exception.MessageManagementException;
import dev.dankov.primes.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.CHAT_NOT_FOUND_MESSAGE;
import static dev.dankov.primes.config.Constants.USER_NOT_FOUND_MESSAGE;

@Service
public class MessageService
{
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository)
    {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public MessageStatusResponseDto createMessage(CreateMessageRequestDto createMessageRequestDto, Principal principal)
    {
        UserEntity user = userRepository.findByUsername(principal.getName())
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
        ChatEntity chat = chatRepository.findById(createMessageRequestDto.getChatId())
            .orElseThrow(() -> new EntityNotFoundException(CHAT_NOT_FOUND_MESSAGE));

        if (!chat.getCreatedBy().equals(user) && !chat.getCreatedWith().equals(user))
        {
            String message = "User is not authorized to send messages in this chat";
            LOGGER.warn(message);
            throw new UnauthorizedException(message);
        }

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setChat(chat);
        messageEntity.setUser(user);
        messageEntity.setMessage(createMessageRequestDto.getMessage());

        try
        {
            MessageEntity savedMessage = messageRepository.save(messageEntity);
            return convertToMessageStatusResponseDto(savedMessage, MessageStatus.CREATED);
        } catch (Exception e)
        {
            String message = String.format("Failed to create message in chat '%s': %s", createMessageRequestDto.getChatId(), e.getMessage());
            LOGGER.warn(message);
            throw new MessageManagementException(message);
        }
    }

    public List<MessageResponseDto> getMessages(UUID chatId, Principal principal)
    {
        UserEntity user = userRepository.findByUsername(principal.getName())
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
        ChatEntity chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new EntityNotFoundException(CHAT_NOT_FOUND_MESSAGE));

        if (!chat.getCreatedBy().equals(user) && !chat.getCreatedWith().equals(user))
        {
            String message = "User is not authorized to read messages in this chat";
            LOGGER.warn(message);
            throw new UnauthorizedException(message);
        }

        List<MessageEntity> messages = messageRepository.findAllByChatId(chatId);
        return messages.stream()
            .map(this::convertToMessageResponseDto)
            .toList();
    }

    private MessageResponseDto convertToMessageResponseDto(MessageEntity messageEntity)
    {
        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setId(messageEntity.getId());
        messageResponseDto.setChatId(messageEntity.getChat().getId());
        messageResponseDto.setUserId(messageEntity.getUser().getId());
        messageResponseDto.setMessage(messageEntity.getMessage());
        messageResponseDto.setCreatedAt(messageEntity.getCreatedAt().atZone(ZoneOffset.UTC).toLocalDateTime());
        return messageResponseDto;
    }

    private MessageStatusResponseDto convertToMessageStatusResponseDto(MessageEntity messageEntity, MessageStatus messageStatus)
    {
        MessageStatusResponseDto messageStatusResponseDto = new MessageStatusResponseDto();
        messageStatusResponseDto.setStatus(messageStatus);
        messageStatusResponseDto.setId(messageEntity.getId());
        messageStatusResponseDto.setChatId(messageEntity.getChat().getId());
        messageStatusResponseDto.setUserId(messageEntity.getUser().getId());
        messageStatusResponseDto.setMessage(messageEntity.getMessage());
        messageStatusResponseDto.setCreatedAt(messageEntity.getCreatedAt().atZone(ZoneOffset.UTC).toLocalDateTime());
        return messageStatusResponseDto;
    }
}
