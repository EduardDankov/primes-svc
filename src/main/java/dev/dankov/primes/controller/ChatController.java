package dev.dankov.primes.controller;

import dev.dankov.primes.dto.request.CreateChatRequestDto;
import dev.dankov.primes.dto.response.ChatResponseDto;
import dev.dankov.primes.dto.response.ChatStatusResponseDto;
import dev.dankov.primes.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/v1/chat")
@Tag(name = "Chat Controller")
public class ChatController
{
    private final ChatService chatService;

    private final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    public ChatController(ChatService chatService)
    {
        this.chatService = chatService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new chat")
    public ResponseEntity<ChatStatusResponseDto> createChat(
        @NonNull
        @Validated
        @RequestBody
        @Parameter(required = true)
        CreateChatRequestDto createChatRequestDto,
        Principal principal
    )
    {
        try
        {
            LOGGER.info("Creating chat between {} and {}", createChatRequestDto.getCreatedBy(), createChatRequestDto.getCreatedWith());
            ChatStatusResponseDto chatStatusResponseDto = chatService.createChat(createChatRequestDto, principal);
            return new ResponseEntity<>(chatStatusResponseDto, HttpStatus.CREATED);
        } finally
        {
            LOGGER.info("Finished createChat between {} and {}", createChatRequestDto.getCreatedBy(), createChatRequestDto.getCreatedWith());
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get chats by user ID")
    public ResponseEntity<List<ChatResponseDto>> getChats(
        @NonNull
        @PathVariable
        @Parameter(required = true)
        UUID userId,

        Principal principal
    )
    {
        try
        {
            LOGGER.info("Getting chats for user {}", userId);
            List<ChatResponseDto> chatResponseDtos = chatService.getChats(userId, principal);
            return new ResponseEntity<>(chatResponseDtos, HttpStatus.OK);
        } finally
        {
            LOGGER.info("Finished getChats for user {}", userId);
        }
    }

    @GetMapping("/{userId}/{chatId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get chat by user ID and chat ID")
    public ResponseEntity<ChatResponseDto> getChat(
        @NonNull
        @PathVariable
        @Parameter(required = true)
        UUID userId,

        @NonNull
        @PathVariable
        @Parameter(required = true)
        UUID chatId,

        Principal principal
    )
    {
        try
        {
            LOGGER.info("Getting chat {} for user {}", chatId, userId);
            ChatResponseDto chatResponseDto = chatService.getChat(userId, chatId, principal);
            return new ResponseEntity<>(chatResponseDto, HttpStatus.OK);
        } finally
        {
            LOGGER.info("Finished getChat for chat {} and user {}", chatId, userId);
        }
    }

    @DeleteMapping("/{userId}/{chatId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete chat by user ID and chat ID")
    public ResponseEntity<Void> deleteChat(
        @NonNull
        @PathVariable
        @Parameter(required = true)
        UUID userId,

        @NonNull
        @PathVariable
        @Parameter(required = true)
        UUID chatId,

        Principal principal
    )
    {
        try
        {
            LOGGER.info("Deleting chat {} for user {}", chatId, userId);
            chatService.deleteChat(userId, chatId, principal);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } finally
        {
            LOGGER.info("Finished deleteChat for chat {} and user {}", chatId, userId);
        }
    }
}
