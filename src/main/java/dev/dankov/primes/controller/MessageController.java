package dev.dankov.primes.controller;

import dev.dankov.primes.dto.request.CreateMessageRequestDto;
import dev.dankov.primes.dto.response.MessageResponseDto;
import dev.dankov.primes.dto.response.MessageStatusResponseDto;
import dev.dankov.primes.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/message")
@Tag(name = "Message Controller")
public class MessageController
{
    private final MessageService messageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    public MessageController(MessageService messageService)
    {
        this.messageService = messageService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new message")
    public ResponseEntity<MessageStatusResponseDto> createMessage(
        @NonNull
        @Validated
        @RequestBody
        @Parameter(required = true)
        CreateMessageRequestDto createMessageRequestDto,
        Principal principal
    )
    {
        try
        {
            LOGGER.info("Creating new message in chat {}", createMessageRequestDto.getChatId());
            MessageStatusResponseDto messageStatusResponseDto = messageService.createMessage(createMessageRequestDto, principal);
            return new ResponseEntity<>(messageStatusResponseDto, HttpStatus.CREATED);
        } finally
        {
            LOGGER.info("Finished createMessage in chat {}", createMessageRequestDto.getChatId());
        }
    }

    @GetMapping("/{chatId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get messages in a chat")
    public ResponseEntity<List<MessageResponseDto>> getMessages(
        @NotNull
        @PathVariable("chatId")
        @Parameter(required = true)
        UUID chatId,
        Principal principal
    )
    {
        try
        {
            LOGGER.info("Getting messages in chat {}", chatId);
            List<MessageResponseDto> messageResponseDtos = messageService.getMessages(chatId, principal);
            return new ResponseEntity<>(messageResponseDtos, HttpStatus.OK);
        } finally
        {
            LOGGER.info("Finished getMessages in chat {}", chatId);
        }
    }
}
