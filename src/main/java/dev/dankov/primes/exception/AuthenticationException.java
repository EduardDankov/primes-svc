package dev.dankov.primes.exception;

public class AuthenticationException extends RuntimeException
{
    public AuthenticationException(String message)
    {
        super(message);
    }
}
