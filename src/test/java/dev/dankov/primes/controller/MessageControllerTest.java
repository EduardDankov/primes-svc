package dev.dankov.primes.controller;

import dev.dankov.primes.dto.request.CreateMessageRequestDto;
import dev.dankov.primes.dto.response.MessageResponseDto;
import dev.dankov.primes.dto.response.MessageStatusResponseDto;
import dev.dankov.primes.enums.MessageStatus;
import dev.dankov.primes.service.MessageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageControllerTest
{
    private static final UUID MESSAGE_ID = UUID.randomUUID();
    private static final UUID CHAT_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String MESSAGE_TEXT = "Hello, World!";
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

    private MessageController messageController;
    private MessageService messageService;

    @BeforeAll
    public void setup()
    {
        messageService = mock(MessageService.class);
        messageController = new MessageController(messageService);
    }

    @Test
    public void createMessageTest()
    {
        CreateMessageRequestDto createMessageRequestDto = new CreateMessageRequestDto();
        createMessageRequestDto.setChatId(CHAT_ID);
        createMessageRequestDto.setMessage(MESSAGE_TEXT);

        MessageStatusResponseDto expectedResponse = new MessageStatusResponseDto();
        expectedResponse.setStatus(MessageStatus.CREATED);
        expectedResponse.setId(MESSAGE_ID);
        expectedResponse.setChatId(CHAT_ID);
        expectedResponse.setUserId(USER_ID);
        expectedResponse.setMessage(MESSAGE_TEXT);
        expectedResponse.setCreatedAt(TIMESTAMP);

        when(messageService.createMessage(any(), any())).thenReturn(expectedResponse);

        ResponseEntity<MessageStatusResponseDto> actualResponse = messageController.createMessage(createMessageRequestDto, null);
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    public void getMessagesTest()
    {
        MessageResponseDto expectedResponse = new MessageResponseDto();
        expectedResponse.setId(MESSAGE_ID);
        expectedResponse.setChatId(CHAT_ID);
        expectedResponse.setUserId(USER_ID);
        expectedResponse.setMessage(MESSAGE_TEXT);
        expectedResponse.setCreatedAt(TIMESTAMP);

        when(messageService.getMessages(any(), any())).thenReturn(List.of(expectedResponse));

        ResponseEntity<List<MessageResponseDto>> actualResponse = messageController.getMessages(CHAT_ID, null);
        assertEquals(List.of(expectedResponse), actualResponse.getBody());
    }
}
