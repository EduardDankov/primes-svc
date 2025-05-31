package dev.dankov.primes.controller;

import dev.dankov.primes.dto.request.CreateUserRequestDto;
import dev.dankov.primes.dto.response.UserResponseDto;
import dev.dankov.primes.dto.response.UserStatusResponseDto;
import dev.dankov.primes.service.UserService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "User Controller")
public class UserController
{
    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserStatusResponseDto> createUser(
        @NonNull
        @Validated
        @RequestBody
        @Parameter(required = true)
        CreateUserRequestDto createUserRequestDto
    )
    {
        try
        {
            LOGGER.info("Creating user for username: {}", createUserRequestDto.getUsername());
            UserStatusResponseDto userStatusResponseDto = userService.createUser(createUserRequestDto);
            return new ResponseEntity<>(userStatusResponseDto, HttpStatus.CREATED);
        } finally
        {
            LOGGER.info("Finished createUser for username: {}", createUserRequestDto.getUsername());
        }
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get users information")
    public ResponseEntity<List<UserResponseDto>> getUsers()
    {
        try
        {
            LOGGER.info("Started getUsers");
            List<UserResponseDto> users = userService.getUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } finally
        {
            LOGGER.info("Finished getUsers");
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get user information by ID")
    public ResponseEntity<UserResponseDto> getUser(
        @PathVariable(name = "id")
        @Parameter(description = "User ID", required = true)
        UUID id
    )
    {
        try
        {
            LOGGER.info("Started getUserById for ID: {}", id);
            UserResponseDto user = userService.getUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } finally
        {
            LOGGER.info("Finished getUserById for ID: {}", id);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update user information by ID")
    public ResponseEntity<UserStatusResponseDto> updateUser(
        @PathVariable(name = "id")
        @Parameter(description = "User ID", required = true)
        UUID id,

        @NonNull
        @Validated
        @RequestBody
        @Parameter(required = true)
        CreateUserRequestDto createUserRequestDto
    )
    {
        try
        {
            LOGGER.info("Started updateUser for ID: {}", id);
            UserStatusResponseDto userStatusResponseDto = userService.updateUser(id, createUserRequestDto);
            return new ResponseEntity<>(userStatusResponseDto, HttpStatus.OK);
        } finally
        {
            LOGGER.info("Finished updateUser for ID: {}", id);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete user by ID")
    public ResponseEntity<Void> deleteUser(
        @PathVariable(name = "id")
        @Parameter(description = "User ID", required = true)
        UUID id
    )
    {
        try
        {
            LOGGER.info("Started deleteUser for ID: {}", id);
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } finally
        {
            LOGGER.info("Finished deleteUser for ID: {}", id);
        }
    }
}
