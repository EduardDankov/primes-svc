package dev.dankov.primes.exception;

public class InvalidArgumentException extends RuntimeException
{
    public InvalidArgumentException(String message)
    {
        super(message);
    }
}
