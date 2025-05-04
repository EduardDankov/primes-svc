package dev.dankov.primes.controller;

import dev.dankov.primes.dto.ErrorDto;
import dev.dankov.primes.dto.request.CreateUserRequestDto;
import dev.dankov.primes.dto.response.UserResponseDto;
import dev.dankov.primes.dto.response.UserStatusResponseDto;
import dev.dankov.primes.enums.UserStatus;
import dev.dankov.primes.service.UserService;
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
public class UserControllerTest
{
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private UserController userController;
    private UserService userService;

    @BeforeAll
    public void setup()
    {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void createUserTest()
    {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();
        createUserRequestDto.setUsername(USERNAME);
        createUserRequestDto.setPassword(PASSWORD);

        UserStatusResponseDto expectedResponse = new UserStatusResponseDto();
        expectedResponse.setStatus(UserStatus.CREATED);
        expectedResponse.setId(USER_ID);
        expectedResponse.setUsername(USERNAME);

        when(userService.createUser(createUserRequestDto)).thenReturn(expectedResponse);

        ResponseEntity<UserStatusResponseDto> actualResponse = userController.createUser(createUserRequestDto);
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    public void getUsersTest()
    {
        UserResponseDto expectedResponse = new UserResponseDto();
        expectedResponse.setId(USER_ID);
        expectedResponse.setUsername(USERNAME);

        when(userService.getUsers()).thenReturn(List.of(expectedResponse));

        ResponseEntity<List<UserResponseDto>> actualResponse = userController.getUsers();
        assertEquals(List.of(expectedResponse), actualResponse.getBody());
    }

    @Test
    public void getUserTest()
    {
        UserResponseDto expectedResponse = new UserResponseDto();
        expectedResponse.setId(USER_ID);
        expectedResponse.setUsername(USERNAME);

        when(userService.getUser(USER_ID)).thenReturn(expectedResponse);

        ResponseEntity<UserResponseDto> actualResponse = userController.getUser(USER_ID);
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    public void updateUserTest()
    {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();
        createUserRequestDto.setUsername(USERNAME);
        createUserRequestDto.setPassword(PASSWORD);

        UserStatusResponseDto expectedResponse = new UserStatusResponseDto();
        expectedResponse.setStatus(UserStatus.UPDATED);
        expectedResponse.setId(USER_ID);
        expectedResponse.setUsername(USERNAME);

        when(userService.updateUser(USER_ID, createUserRequestDto)).thenReturn(expectedResponse);

        ResponseEntity<UserStatusResponseDto> actualResponse = userController.updateUser(USER_ID, createUserRequestDto);
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    public void deleteUserTest()
    {
        doNothing().when(userService).deleteUser(USER_ID);

        ResponseEntity<Void> actualResponse = userController.deleteUser(USER_ID);

        assertEquals(HttpStatus.NO_CONTENT, actualResponse.getStatusCode());
    }
}
