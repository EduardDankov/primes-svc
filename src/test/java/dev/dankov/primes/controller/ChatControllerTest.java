package dev.dankov.primes.controller;

import dev.dankov.primes.dto.request.CreateChatRequestDto;
import dev.dankov.primes.dto.response.ChatResponseDto;
import dev.dankov.primes.dto.response.ChatStatusResponseDto;
import dev.dankov.primes.enums.ChatStatus;
import dev.dankov.primes.service.ChatService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChatControllerTest
{
    private static final UUID CHAT_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID USER_ID2 = UUID.randomUUID();

    private ChatController chatController;
    private ChatService chatService;

    @BeforeAll
    public void setup()
    {
        chatService = mock(ChatService.class);
        chatController = new ChatController(chatService);
    }

    @Test
    public void createChatTest()
    {
        CreateChatRequestDto requestDto = new CreateChatRequestDto();
        requestDto.setCreatedBy(USER_ID);
        requestDto.setCreatedWith(USER_ID2);

        ChatStatusResponseDto expectedResponse = new ChatStatusResponseDto();
        expectedResponse.setStatus(ChatStatus.CREATED);
        expectedResponse.setId(CHAT_ID);
        expectedResponse.setCreatedBy(USER_ID);
        expectedResponse.setCreatedWith(USER_ID2);

        when(chatService.createChat(any(), any())).thenReturn(expectedResponse);

        ResponseEntity<ChatStatusResponseDto> actualResponse = chatController.createChat(requestDto, null);
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    public void getChatsTest()
    {
        ChatResponseDto expectedResponse = new ChatResponseDto();
        expectedResponse.setId(CHAT_ID);
        expectedResponse.setCreatedBy(USER_ID);
        expectedResponse.setCreatedWith(USER_ID2);

        when(chatService.getChats(any(), any())).thenReturn(List.of(expectedResponse));

        ResponseEntity<List<ChatResponseDto>> actualResponse = chatController.getChats(USER_ID, null);
        assertEquals(List.of(expectedResponse), actualResponse.getBody());
    }

    @Test
    public void getChatTest()
    {
        ChatResponseDto expectedResponse = new ChatResponseDto();
        expectedResponse.setId(CHAT_ID);
        expectedResponse.setCreatedBy(USER_ID);
        expectedResponse.setCreatedWith(USER_ID2);

        when(chatService.getChat(any(), any(), any())).thenReturn(expectedResponse);

        ResponseEntity<ChatResponseDto> actualResponse = chatController.getChat(USER_ID, CHAT_ID, null);
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    public void deleteChatTest()
    {
        doNothing().when(chatService).deleteChat(USER_ID, CHAT_ID, null);

        ResponseEntity<Void> actualResponse = chatController.deleteChat(USER_ID, CHAT_ID, null);
        assertEquals(HttpStatus.NO_CONTENT, actualResponse.getStatusCode());
    }
}
