package dev.dankov.primes.controller;

import dev.dankov.primes.dto.request.CreateUserRequestDto;
import dev.dankov.primes.dto.response.UserStatusResponseDto;
import dev.dankov.primes.entity.UserEntity;
import dev.dankov.primes.enums.UserStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControllerTest
{
    private static final String INVALID_CREDENTIALS = "Invalid username or password";
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private LoginController loginController;
    private AuthenticationManager authenticationManager;
    private Authentication authentication;

    @BeforeAll
    public void setup()
    {
        authenticationManager = mock(AuthenticationManager.class);
        authentication = mock(Authentication.class);
        loginController = new LoginController(authenticationManager);
    }

    @Test
    public void loginTestValid()
    {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();
        createUserRequestDto.setUsername(USERNAME);
        createUserRequestDto.setPassword(PASSWORD);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);
        userEntity.setPassword(PASSWORD);

        UserStatusResponseDto expectedResponse = new UserStatusResponseDto();
        expectedResponse.setStatus(UserStatus.AUTHENTICATED);
        expectedResponse.setId(USER_ID);
        expectedResponse.setUsername(USERNAME);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getDetails()).thenReturn(userEntity);

        ResponseEntity<UserStatusResponseDto> response = loginController.login(createUserRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void loginTestInvalid()
    {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        try
        {
            loginController.login(createUserRequestDto);
            fail("Expected BadCredentialsException to be thrown");
        } catch (BadCredentialsException e)
        {
            assertEquals(INVALID_CREDENTIALS, e.getMessage());
        }
    }
}
