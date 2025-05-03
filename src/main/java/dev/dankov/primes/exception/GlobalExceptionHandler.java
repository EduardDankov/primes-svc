package dev.dankov.primes.exception;

import dev.dankov.primes.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ErrorDto> handleInvalidArgumentException(InvalidArgumentException e)
    {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserManagementException.class)
    public ResponseEntity<ErrorDto> handleUserManagementException(UserManagementException e)
    {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
