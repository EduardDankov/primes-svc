package dev.dankov.primes.exception;

import dev.dankov.primes.dto.ErrorDto;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static dev.dankov.primes.config.Constants.ACCESS_DENIED_MESSAGE;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationException(AuthenticationException e)
    {
        LOGGER.warn(e.getMessage());
        return new ResponseEntity<>(new ErrorDto(ACCESS_DENIED_MESSAGE), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ChatManagementException.class)
    public ResponseEntity<ErrorDto> handleChatManagementException(ChatManagementException e)
    {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(EntityNotFoundException e)
    {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ErrorDto> handleInvalidArgumentException(InvalidArgumentException e)
    {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessageManagementException.class)
    public ResponseEntity<ErrorDto> handleMessageManagementException(MessageManagementException e)
    {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDto> handleUnauthorizedException(UnauthorizedException e)
    {
        return new ResponseEntity<>(new ErrorDto(ACCESS_DENIED_MESSAGE), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserManagementException.class)
    public ResponseEntity<ErrorDto> handleUserManagementException(UserManagementException e)
    {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
