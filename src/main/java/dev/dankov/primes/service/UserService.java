package dev.dankov.primes.service;

import dev.dankov.primes.dao.UserRepository;
import dev.dankov.primes.dto.request.CreateUserRequestDto;
import dev.dankov.primes.dto.response.UserStatusResponseDto;
import dev.dankov.primes.entity.UserEntity;
import dev.dankov.primes.enums.UserStatus;
import dev.dankov.primes.exception.UserManagementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dev.dankov.primes.config.Constants.USERNAME_TAKEN_MESSAGE;

@Service
public class UserService
{
    private final UserRepository userRepository;

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
        userEntity.setPassword(createUserRequestDto.getPassword());

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

    private UserStatusResponseDto convertToUserStatusResponseDto(UserEntity user, UserStatus status)
    {
        UserStatusResponseDto userStatusResponseDto = new UserStatusResponseDto();
        userStatusResponseDto.setStatus(status);
        userStatusResponseDto.setId(user.getId());
        userStatusResponseDto.setUsername(user.getUsername());
        return userStatusResponseDto;
    }
}
