package dev.dankov.primes.exception;

import dev.dankov.primes.dto.ErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static dev.dankov.primes.config.Constants.ACCESS_DENIED_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTest
{
    private final String ERROR_MESSAGE = "error message";

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void handleAuthenticationExceptionTest()
    {
        AuthenticationException exception = new AuthenticationException(ERROR_MESSAGE);
        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleAuthenticationException(exception);
        assertNotNull(response.getBody());
        assertEquals(ACCESS_DENIED_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void handleChatManagementExceptionTest()
    {
        ChatManagementException exception = new ChatManagementException(ERROR_MESSAGE);
        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleChatManagementException(exception);
        assertNotNull(response.getBody());
        assertEquals(ERROR_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void handleEntityNotFoundExceptionTest()
    {
        EntityNotFoundException exception = new EntityNotFoundException(ERROR_MESSAGE);
        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleEntityNotFoundException(exception);
        assertNotNull(response.getBody());
        assertEquals(ERROR_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void handleInvalidArgumentExceptionTest()
    {
        InvalidArgumentException exception = new InvalidArgumentException(ERROR_MESSAGE);
        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleInvalidArgumentException(exception);
        assertNotNull(response.getBody());
        assertEquals(ERROR_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void handleMessageManagementExceptionTest()
    {
        MessageManagementException exception = new MessageManagementException(ERROR_MESSAGE);
        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleMessageManagementException(exception);
        assertNotNull(response.getBody());
        assertEquals(ERROR_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void handleUnauthorizedExceptionTest()
    {
        UnauthorizedException exception = new UnauthorizedException(ERROR_MESSAGE);
        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleUnauthorizedException(exception);
        assertNotNull(response.getBody());
        assertEquals(ACCESS_DENIED_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void handleUserManagementExceptionTest()
    {
        UserManagementException exception = new UserManagementException(ERROR_MESSAGE);
        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleUserManagementException(exception);
        assertNotNull(response.getBody());
        assertEquals(ERROR_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
