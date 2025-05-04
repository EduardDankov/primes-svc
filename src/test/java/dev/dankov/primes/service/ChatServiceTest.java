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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.*;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChatServiceTest
{
    private static final UUID CHAT_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID USER_ID2 = UUID.randomUUID();
    private static final String USERNAME = "username";
    private static final String USERNAME2 = "username2";

    ChatService chatService;
    ChatRepository chatRepository;
    UserRepository userRepository;
    Principal principal;

    @BeforeAll
    public void setup()
    {
        this.chatRepository = mock(ChatRepository.class);
        this.userRepository = mock(UserRepository.class);
        this.principal = mock(Principal.class);
        when(principal.getName()).thenReturn(USERNAME);
        this.chatService = new ChatService(chatRepository, userRepository);
    }

    @Test
    public void createChatTestValid()
    {
        CreateChatRequestDto createChatRequestDto = new CreateChatRequestDto();
        createChatRequestDto.setCreatedBy(USER_ID);
        createChatRequestDto.setCreatedWith(USER_ID2);

        UserEntity user = new UserEntity();
        user.setUsername(USERNAME);
        user.setId(USER_ID);

        UserEntity user2 = new UserEntity();
        user2.setUsername(USERNAME2);
        user2.setId(USER_ID2);

        ChatEntity chat = new ChatEntity();
        chat.setId(CHAT_ID);
        chat.setCreatedBy(user);
        chat.setCreatedWith(user2);

        ChatStatusResponseDto expectedResponse = new ChatStatusResponseDto();
        expectedResponse.setStatus(ChatStatus.CREATED);
        expectedResponse.setId(CHAT_ID);
        expectedResponse.setCreatedBy(USER_ID);
        expectedResponse.setCreatedWith(USER_ID2);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.findById(USER_ID2)).thenReturn(Optional.of(user2));
        when(chatRepository.findByUserIds(USER_ID, USER_ID2)).thenReturn(Optional.empty());
        when(chatRepository.save(any())).thenReturn(chat);

        ChatStatusResponseDto actualResponse = chatService.createChat(createChatRequestDto, principal);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createChatTestUserNotFound()
    {
        CreateChatRequestDto createChatRequestDto = new CreateChatRequestDto();
        createChatRequestDto.setCreatedBy(USER_ID);
        createChatRequestDto.setCreatedWith(USER_ID2);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        try
        {
            chatService.createChat(createChatRequestDto, principal);
            fail("Expected EntityNotFoundException to be thrown");
        }
        catch (EntityNotFoundException e)
        {
            assertEquals(USER_NOT_FOUND_MESSAGE, e.getMessage());
        }
    }

    @Test
    public void createChatTestConflict()
    {
        CreateChatRequestDto createChatRequestDto = new CreateChatRequestDto();
        createChatRequestDto.setCreatedBy(USER_ID);
        createChatRequestDto.setCreatedWith(USER_ID2);

        UserEntity user = new UserEntity();
        user.setUsername(USERNAME);
        user.setId(USER_ID);

        UserEntity user2 = new UserEntity();
        user2.setUsername(USERNAME2);
        user2.setId(USER_ID2);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.findById(USER_ID2)).thenReturn(Optional.of(user2));
        when(chatRepository.findByUserIds(USER_ID, USER_ID2)).thenReturn(Optional.of(new ChatEntity()));

        try
        {
            chatService.createChat(createChatRequestDto, principal);
            fail("Expected ChatManagementException to be thrown");
        }
        catch (ChatManagementException e)
        {
            var expectedMessage = String.format(CHAT_CONFLICT_MESSAGE, USER_ID, USER_ID2);
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void createChatTestUnauthorized()
    {
        CreateChatRequestDto createChatRequestDto = new CreateChatRequestDto();
        createChatRequestDto.setCreatedBy(USER_ID);
        createChatRequestDto.setCreatedWith(USER_ID2);

        UserEntity user = new UserEntity();
        user.setUsername(USERNAME);
        user.setId(USER_ID);

        UserEntity user2 = new UserEntity();
        user.setUsername(USERNAME2);
        user.setId(USER_ID2);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.findById(USER_ID2)).thenReturn(Optional.of(user2));

        try
        {
            chatService.createChat(createChatRequestDto, principal);
            fail("Expected UnauthorizedException to be thrown");
        }
        catch (UnauthorizedException e)
        {
            assertEquals("User is not authorized to create chat as another user", e.getMessage());
        }
    }

    @Test
    public void getChatsTestValid()
    {
        ChatResponseDto chatResponseDto = new ChatResponseDto();
        chatResponseDto.setId(CHAT_ID);
        chatResponseDto.setCreatedBy(USER_ID);
        chatResponseDto.setCreatedWith(USER_ID2);

        UserEntity user = new UserEntity();
        user.setUsername(USERNAME);
        user.setId(USER_ID);

        UserEntity user2 = new UserEntity();
        user2.setUsername(USERNAME2);
        user2.setId(USER_ID2);

        ChatEntity chat = new ChatEntity();
        chat.setId(CHAT_ID);
        chat.setCreatedBy(user);
        chat.setCreatedWith(user2);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(chatRepository.findAllByUserId(USER_ID)).thenReturn(List.of(chat));

        List<ChatResponseDto> actualResponse = chatService.getChats(USER_ID, principal);

        assertEquals(List.of(chatResponseDto), actualResponse);
    }

    @Test
    public void getChatsTestUnauthorized()
    {
        UserEntity user = new UserEntity();
        user.setUsername(USERNAME2);
        user.setId(USER_ID2);

        when(userRepository.findById(USER_ID2)).thenReturn(Optional.of(user));

        try
        {
            chatService.getChats(USER_ID2, principal);
            fail("Expected UnauthorizedException to be thrown");
        }
        catch (UnauthorizedException e)
        {
            assertEquals("User is not authorized to view chats of another user", e.getMessage());
        }
    }

    @Test
    public void getChatTestValid()
    {
        ChatResponseDto chatResponseDto = new ChatResponseDto();
        chatResponseDto.setId(CHAT_ID);
        chatResponseDto.setCreatedBy(USER_ID);
        chatResponseDto.setCreatedWith(USER_ID2);

        UserEntity user = new UserEntity();
        user.setUsername(USERNAME);
        user.setId(USER_ID);

        UserEntity user2 = new UserEntity();
        user2.setUsername(USERNAME2);
        user2.setId(USER_ID2);

        ChatEntity chat = new ChatEntity();
        chat.setId(CHAT_ID);
        chat.setCreatedBy(user);
        chat.setCreatedWith(user2);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(chatRepository.findByUserIdAndChatId(USER_ID, CHAT_ID)).thenReturn(Optional.of(chat));

        ChatResponseDto actualResponse = chatService.getChat(USER_ID, CHAT_ID, principal);

        assertEquals(chatResponseDto, actualResponse);
    }

    @Test
    public void getChatTestUnauthorized()
    {
        UserEntity user = new UserEntity();
        user.setUsername(USERNAME2);
        user.setId(USER_ID2);

        when(userRepository.findById(USER_ID2)).thenReturn(Optional.of(user));

        try
        {
            chatService.getChat(USER_ID2, CHAT_ID, principal);
            fail("Expected UnauthorizedException to be thrown");
        }
        catch (UnauthorizedException e)
        {
            assertEquals("User is not authorized to view chats of another user", e.getMessage());
        }
    }

    @Test
    public void getChatTestNotFound()
    {
        UserEntity user = new UserEntity();
        user.setUsername(USERNAME);
        user.setId(USER_ID);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(chatRepository.findByUserIdAndChatId(USER_ID, CHAT_ID)).thenReturn(Optional.empty());

        try
        {
            chatService.getChat(USER_ID, CHAT_ID, principal);
            fail("Expected EntityNotFoundException to be thrown");
        }
        catch (EntityNotFoundException e)
        {
            assertEquals(CHAT_NOT_FOUND_MESSAGE, e.getMessage());
        }
    }

    @Test
    public void deleteChatTestValid()
    {
        UserEntity user = new UserEntity();
        user.setUsername(USERNAME);
        user.setId(USER_ID);

        UserEntity user2 = new UserEntity();
        user2.setUsername(USERNAME2);
        user2.setId(USER_ID2);

        ChatEntity chat = new ChatEntity();
        chat.setId(CHAT_ID);
        chat.setCreatedBy(user);
        chat.setCreatedWith(user2);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(chatRepository.findByUserIdAndChatId(USER_ID, CHAT_ID)).thenReturn(Optional.of(chat));
        doNothing().when(chatRepository).delete(chat);

        chatService.deleteChat(USER_ID, CHAT_ID, principal);
    }

    @Test
    public void deleteChatTestUnauthorized()
    {
        UserEntity user = new UserEntity();
        user.setUsername(USERNAME);
        user.setId(USER_ID);

        UserEntity user2 = new UserEntity();
        user2.setUsername(USERNAME2);
        user2.setId(USER_ID2);

        ChatEntity chat = new ChatEntity();
        chat.setId(CHAT_ID);
        chat.setCreatedBy(user);
        chat.setCreatedWith(user);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.findById(USER_ID2)).thenReturn(Optional.of(user2));
        when(chatRepository.findByUserIdAndChatId(USER_ID, CHAT_ID)).thenReturn(Optional.of(chat));
        doNothing().when(chatRepository).delete(chat);

        try
        {
            chatService.deleteChat(USER_ID2, CHAT_ID, principal);
            fail("Expected UnauthorizedException to be thrown");
        } catch (UnauthorizedException e)
        {
            assertEquals("User is not authorized to delete chats of another user", e.getMessage());
        }
    }

    @Test
    public void deleteChatTestNotFound()
    {
        UserEntity user = new UserEntity();
        user.setUsername(USERNAME);
        user.setId(USER_ID);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(chatRepository.findByUserIdAndChatId(USER_ID, CHAT_ID)).thenReturn(Optional.empty());

        try
        {
            chatService.deleteChat(USER_ID, CHAT_ID, principal);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e)
        {
            assertEquals(CHAT_NOT_FOUND_MESSAGE, e.getMessage());
        }
    }
}
