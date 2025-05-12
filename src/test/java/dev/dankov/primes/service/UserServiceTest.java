package dev.dankov.primes.service;

import dev.dankov.primes.dao.UserRepository;
import dev.dankov.primes.dto.request.CreateUserRequestDto;
import dev.dankov.primes.dto.response.UserResponseDto;
import dev.dankov.primes.dto.response.UserStatusResponseDto;
import dev.dankov.primes.entity.UserEntity;
import dev.dankov.primes.enums.UserStatus;
import dev.dankov.primes.exception.EntityNotFoundException;
import dev.dankov.primes.exception.UserManagementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.USERNAME_TAKEN_MESSAGE;
import static dev.dankov.primes.config.Constants.USER_NOT_FOUND_MESSAGE;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest
{
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private UserService userService;
    private UserRepository userRepository;

    @BeforeAll
    public void setup()
    {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void createUserTestValid()
    {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();
        createUserRequestDto.setUsername(USERNAME);
        createUserRequestDto.setPassword(PASSWORD);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);
        userEntity.setPassword(PASSWORD);

        UserStatusResponseDto expectedResponse = new UserStatusResponseDto();
        expectedResponse.setStatus(UserStatus.CREATED);
        expectedResponse.setId(USER_ID);
        expectedResponse.setUsername(USERNAME);

        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(userEntity);

        UserStatusResponseDto actualResponse = userService.createUser(createUserRequestDto);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createUserTestUsernameTaken()
    {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();
        createUserRequestDto.setUsername(USERNAME);
        createUserRequestDto.setPassword(PASSWORD);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);
        userEntity.setPassword(PASSWORD);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(userEntity));

        try
        {
            userService.createUser(createUserRequestDto);
            fail("Expected UserManagementException to be thrown");
        } catch (UserManagementException e)
        {
            String message = String.format(USERNAME_TAKEN_MESSAGE, USERNAME);
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void getUsersTest()
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);
        userEntity.setPassword(PASSWORD);

        UserResponseDto expectedResponse = new UserResponseDto();
        expectedResponse.setId(USER_ID);
        expectedResponse.setUsername(USERNAME);

        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        List<UserResponseDto> actualResponse = userService.getUsers();
        assertEquals(1, actualResponse.size());
        assertEquals(List.of(expectedResponse), actualResponse);
    }

    @Test
    public void getUserTestValid()
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);
        userEntity.setPassword(PASSWORD);

        UserResponseDto expectedResponse = new UserResponseDto();
        expectedResponse.setId(USER_ID);
        expectedResponse.setUsername(USERNAME);

        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));

        UserResponseDto actualResponse = userService.getUser(USER_ID);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getUserTestInvalid()
    {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        try
        {
            userService.getUser(USER_ID);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e)
        {
            assertEquals(USER_NOT_FOUND_MESSAGE, e.getMessage());
        }
    }

    @Test
    public void updateUserTestValid()
    {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();
        createUserRequestDto.setUsername(USERNAME);
        createUserRequestDto.setPassword(PASSWORD);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);
        userEntity.setPassword(PASSWORD);

        UserStatusResponseDto expectedResponse = new UserStatusResponseDto();
        expectedResponse.setStatus(UserStatus.UPDATED);
        expectedResponse.setId(USER_ID);
        expectedResponse.setUsername(USERNAME);

        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any())).thenReturn(userEntity);

        UserStatusResponseDto actualResponse = userService.updateUser(USER_ID, createUserRequestDto);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void updateUserTestInvalidUser()
    {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();
        createUserRequestDto.setUsername(USERNAME);
        createUserRequestDto.setPassword(PASSWORD);

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        try
        {
            userService.updateUser(USER_ID, createUserRequestDto);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e)
        {
            assertEquals(USER_NOT_FOUND_MESSAGE, e.getMessage());
        }
    }

    @Test
    public void deleteUserTestValid()
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);
        userEntity.setPassword(PASSWORD);

        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));

        userService.deleteUser(USER_ID);
    }

    @Test
    public void deleteUserTestInvalid()
    {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        try
        {
            userService.deleteUser(USER_ID);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e)
        {
            assertEquals(USER_NOT_FOUND_MESSAGE, e.getMessage());
        }
    }
}
