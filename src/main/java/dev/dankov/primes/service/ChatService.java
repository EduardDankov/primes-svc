package dev.dankov.primes.service;

import dev.dankov.primes.dao.ChatRepository;
import dev.dankov.primes.dao.UserRepository;
import dev.dankov.primes.dto.request.CreateChatRequestDto;
import dev.dankov.primes.dto.response.ChatResponseDto;
import dev.dankov.primes.dto.response.ChatStatusResponseDto;
import dev.dankov.primes.entity.ChatEntity;
import dev.dankov.primes.entity.UserEntity;
import dev.dankov.primes.enums.ChatStatus;
import dev.dankov.primes.exception.ChatManagementException;
import dev.dankov.primes.exception.EntityNotFoundException;
import dev.dankov.primes.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.*;

@Service
public class ChatService
{
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository)
    {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public ChatStatusResponseDto createChat(CreateChatRequestDto createChatRequestDto, Principal principal)
    {
        UserEntity createdByUser = userRepository.findById(createChatRequestDto.getCreatedBy())
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
        UserEntity createdWithUser = userRepository.findById(createChatRequestDto.getCreatedWith())
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (!createdByUser.getUsername().equals(principal.getName()))
        {
            var message = "User is not authorized to create chat as another user";
            LOGGER.warn(message);
            throw new UnauthorizedException(message);
        }

        if (chatRepository.findByUserIds(createdByUser.getId(), createdWithUser.getId()).isPresent())
        {
            var message = String.format(CHAT_CONFLICT_MESSAGE, createChatRequestDto.getCreatedBy(),
                createChatRequestDto.getCreatedWith());
            LOGGER.warn(message);
            throw new ChatManagementException(message);
        }

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setCreatedBy(createdByUser);
        chatEntity.setCreatedWith(createdWithUser);

        try
        {
            ChatEntity chat = chatRepository.save(chatEntity);
            return convertToChatStatusResponseDto(chat, ChatStatus.CREATED);
        } catch (Exception e)
        {
            var message = String.format("Failed to create chat between %s and %s: %s", createChatRequestDto.getCreatedBy(),
                createChatRequestDto.getCreatedWith(), e.getMessage());
            LOGGER.warn(message);
            throw new ChatManagementException(message);
        }
    }

    public List<ChatResponseDto> getChats(UUID userId, Principal principal)
    {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (!user.getUsername().equals(principal.getName()))
        {
            var message = "User is not authorized to view chats of another user";
            LOGGER.warn(message);
            throw new UnauthorizedException(message);
        }

        List<ChatEntity> chatEntities = chatRepository.findAllByUserId(userId);
        return chatEntities.stream()
            .map(this::convertToChatResponseDto)
            .toList();
    }

    public ChatResponseDto getChat(UUID userId, UUID chatId, Principal principal)
    {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (!user.getUsername().equals(principal.getName()))
        {
            var message = "User is not authorized to view chats of another user";
            LOGGER.warn(message);
            throw new UnauthorizedException(message);
        }

        ChatEntity chatEntity = chatRepository.findByUserIdAndChatId(userId, chatId)
            .orElseThrow(() -> new EntityNotFoundException(CHAT_NOT_FOUND_MESSAGE));

        return convertToChatResponseDto(chatEntity);
    }

    public void deleteChat(UUID userId, UUID chatId, Principal principal)
    {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (!user.getUsername().equals(principal.getName()))
        {
            var message = "User is not authorized to delete chats of another user";
            LOGGER.warn(message);
            throw new UnauthorizedException(message);
        }

        ChatEntity chatEntity = chatRepository.findByUserIdAndChatId(userId, chatId)
            .orElseThrow(() -> new EntityNotFoundException(CHAT_NOT_FOUND_MESSAGE));

        chatRepository.delete(chatEntity);
    }

    private ChatResponseDto convertToChatResponseDto(ChatEntity chatEntity)
    {
        ChatResponseDto chatResponseDto = new ChatResponseDto();
        chatResponseDto.setId(chatEntity.getId());
        chatResponseDto.setCreatedBy(chatEntity.getCreatedBy().getId());
        chatResponseDto.setCreatedWith(chatEntity.getCreatedWith().getId());
        return chatResponseDto;
    }

    private ChatStatusResponseDto convertToChatStatusResponseDto(ChatEntity chat, ChatStatus status)
    {
        ChatStatusResponseDto chatStatusResponseDto = new ChatStatusResponseDto();
        chatStatusResponseDto.setId(chat.getId());
        chatStatusResponseDto.setCreatedBy(chat.getCreatedBy().getId());
        chatStatusResponseDto.setCreatedWith(chat.getCreatedWith().getId());
        chatStatusResponseDto.setStatus(status);
        return chatStatusResponseDto;
    }
}
