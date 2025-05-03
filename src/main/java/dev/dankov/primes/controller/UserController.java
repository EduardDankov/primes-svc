package dev.dankov.primes.controller;

import dev.dankov.primes.dto.request.CreateUserRequestDto;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/user")
@Tag(name = "User Controller")
public class UserController
{
    private final UserService userService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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
}
