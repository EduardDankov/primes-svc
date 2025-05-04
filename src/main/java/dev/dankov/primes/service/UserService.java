package dev.dankov.primes.service;

import dev.dankov.primes.dao.UserRepository;
import dev.dankov.primes.dto.request.CreateUserRequestDto;
import dev.dankov.primes.dto.response.UserResponseDto;
import dev.dankov.primes.dto.response.UserStatusResponseDto;
import dev.dankov.primes.entity.UserEntity;
import dev.dankov.primes.enums.UserStatus;
import dev.dankov.primes.exception.InvalidArgumentException;
import dev.dankov.primes.exception.UserManagementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.USERNAME_TAKEN_MESSAGE;
import static dev.dankov.primes.config.Constants.USER_NOT_FOUND_MESSAGE;

@Service
public class UserService
{
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public UserStatusResponseDto createUser(CreateUserRequestDto createUserRequestDto)
    {
        if (userRepository.findByUsername(createUserRequestDto.getUsername()).isPresent())
        {
            String message = String.format(USERNAME_TAKEN_MESSAGE, createUserRequestDto.getUsername());
            LOGGER.warn(message);
            throw new UserManagementException(message);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(createUserRequestDto.getUsername());
        userEntity.setPassword(encode(createUserRequestDto.getPassword()));

        try
        {
            UserEntity user = userRepository.save(userEntity);
            return convertToUserStatusResponseDto(user, UserStatus.CREATED);
        } catch (Exception e)
        {
            var message = String.format("Failed to create user with username '%s': %s", createUserRequestDto.getUsername(), e.getMessage());
            LOGGER.warn(message);
            throw new UserManagementException(message);
        }
    }

    public List<UserResponseDto> getUsers()
    {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
            .map(this::convertToUserResponseDto)
            .toList();
    }

    public UserResponseDto getUser(UUID userId)
    {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new InvalidArgumentException(USER_NOT_FOUND_MESSAGE));
        return convertToUserResponseDto(user);
    }

    public UserStatusResponseDto updateUser(UUID userId, CreateUserRequestDto createUserRequestDto)
    {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new InvalidArgumentException(USER_NOT_FOUND_MESSAGE));

        user.setUsername(createUserRequestDto.getUsername());
        user.setPassword(encode(createUserRequestDto.getPassword()));

        try
        {
            UserEntity updatedUser = userRepository.save(user);
            return convertToUserStatusResponseDto(updatedUser, UserStatus.UPDATED);
        } catch (Exception e)
        {
            var message = String.format("Failed to update user with ID '%s': %s", userId, e.getMessage());
            LOGGER.warn(message);
            throw new UserManagementException(message);
        }
    }

    public void deleteUser(UUID userId)
    {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new InvalidArgumentException(USER_NOT_FOUND_MESSAGE));

        try
        {
            userRepository.delete(user);
        } catch (Exception e)
        {
            var message = String.format("Failed to delete user with ID '%s': %s", userId, e.getMessage());
            LOGGER.warn(message);
            throw new UserManagementException(message);
        }
    }

    private UserResponseDto convertToUserResponseDto(UserEntity user)
    {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        return userResponseDto;
    }

    private UserStatusResponseDto convertToUserStatusResponseDto(UserEntity user, UserStatus status)
    {
        UserStatusResponseDto userStatusResponseDto = new UserStatusResponseDto();
        userStatusResponseDto.setStatus(status);
        userStatusResponseDto.setId(user.getId());
        userStatusResponseDto.setUsername(user.getUsername());
        return userStatusResponseDto;
    }

    private String encode(String password)
    {
        return encoder.encode(password);
    }
}
