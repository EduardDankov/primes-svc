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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageServiceTest
{
    private static final UUID MESSAGE_ID = UUID.randomUUID();
    private static final UUID CHAT_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID USER_ID2 = UUID.randomUUID();
    private static final String USERNAME = "username";
    private static final String USERNAME2 = "username2";
    private static final String MESSAGE_TEXT = "Hello, World!";
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

    private MessageService messageService;
    private MessageRepository messageRepository;
    private ChatRepository chatRepository;
    private UserRepository userRepository;
    private Principal principal;

    @BeforeAll
    public void setup()
    {
        this.messageRepository = mock(MessageRepository.class);
        this.chatRepository = mock(ChatRepository.class);
        this.userRepository = mock(UserRepository.class);
        this.principal = mock(Principal.class);
        this.messageService = new MessageService(messageRepository, chatRepository, userRepository);
    }

    @Test
    public void createMessageTestValid()
    {
        CreateMessageRequestDto createMessageRequestDto = new CreateMessageRequestDto();
        createMessageRequestDto.setChatId(CHAT_ID);
        createMessageRequestDto.setMessage(MESSAGE_TEXT);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(CHAT_ID);
        chatEntity.setCreatedBy(userEntity);
        chatEntity.setCreatedWith(userEntity);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(MESSAGE_ID);
        messageEntity.setChat(chatEntity);
        messageEntity.setUser(userEntity);
        messageEntity.setMessage(MESSAGE_TEXT);
        messageEntity.setCreatedAt(TIMESTAMP.toInstant(ZoneOffset.UTC));

        MessageStatusResponseDto expectedResponse = new MessageStatusResponseDto();
        expectedResponse.setStatus(MessageStatus.CREATED);
        expectedResponse.setId(MESSAGE_ID);
        expectedResponse.setChatId(CHAT_ID);
        expectedResponse.setUserId(USER_ID);
        expectedResponse.setMessage(MESSAGE_TEXT);
        expectedResponse.setCreatedAt(TIMESTAMP);

        when(principal.getName()).thenReturn(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));
        when(chatRepository.findById(CHAT_ID)).thenReturn(Optional.of(chatEntity));
        when(messageRepository.save(any())).thenReturn(messageEntity);

        MessageStatusResponseDto actualResponse = messageService.createMessage(createMessageRequestDto, principal);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createMessageTestUnauthorized()
    {
        CreateMessageRequestDto createMessageRequestDto = new CreateMessageRequestDto();
        createMessageRequestDto.setChatId(CHAT_ID);
        createMessageRequestDto.setMessage(MESSAGE_TEXT);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(USER_ID2);
        userEntity2.setUsername(USERNAME);

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(CHAT_ID);
        chatEntity.setCreatedBy(userEntity2);
        chatEntity.setCreatedWith(userEntity2);

        when(principal.getName()).thenReturn(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));
        when(chatRepository.findById(CHAT_ID)).thenReturn(Optional.of(chatEntity));

        try
        {
            messageService.createMessage(createMessageRequestDto, principal);
            fail("Expected UnauthorizedException to be thrown");
        }
        catch (Exception e)
        {
            assertEquals("User is not authorized to send messages in this chat", e.getMessage());
        }
    }

    @Test
    public void getMessagesTestValid()
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(CHAT_ID);
        chatEntity.setCreatedBy(userEntity);
        chatEntity.setCreatedWith(userEntity);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(MESSAGE_ID);
        messageEntity.setChat(chatEntity);
        messageEntity.setUser(userEntity);
        messageEntity.setMessage(MESSAGE_TEXT);
        messageEntity.setCreatedAt(TIMESTAMP.toInstant(ZoneOffset.UTC));

        MessageResponseDto expectedResponse = new MessageResponseDto();
        expectedResponse.setId(MESSAGE_ID);
        expectedResponse.setChatId(CHAT_ID);
        expectedResponse.setUserId(USER_ID);
        expectedResponse.setMessage(MESSAGE_TEXT);
        expectedResponse.setCreatedAt(TIMESTAMP);

        when(principal.getName()).thenReturn(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));
        when(chatRepository.findById(CHAT_ID)).thenReturn(Optional.of(chatEntity));
        when(messageRepository.findAllByChatId(CHAT_ID)).thenReturn(List.of(messageEntity));

        List<MessageResponseDto> actualResponse = messageService.getMessages(CHAT_ID, principal);

        assertEquals(List.of(expectedResponse), actualResponse);
    }

    @Test
    public void getMessagesTestUnauthorized()
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(USER_ID2);
        userEntity2.setUsername(USERNAME);

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(CHAT_ID);
        chatEntity.setCreatedBy(userEntity2);
        chatEntity.setCreatedWith(userEntity2);

        when(principal.getName()).thenReturn(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));
        when(chatRepository.findById(CHAT_ID)).thenReturn(Optional.of(chatEntity));

        try
        {
            messageService.getMessages(CHAT_ID, principal);
            fail("Expected UnauthorizedException to be thrown");
        }
        catch (Exception e)
        {
            assertEquals("User is not authorized to read messages in this chat", e.getMessage());
        }
    }
}
