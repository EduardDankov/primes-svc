package dev.dankov.primes.enums;

import dev.dankov.primes.exception.InvalidArgumentException;

import static dev.dankov.primes.config.Constants.INVALID_ENUM_MESSAGE;

public enum MessageStatus
{
    CREATED("CREATED"),
    FAILED("FAILED");

    private final String status;

    MessageStatus(String status)
    {
        this.status = status;
    }

    public static MessageStatus fromString(String status)
    {
        for (MessageStatus messageStatus : MessageStatus.values())
        {
            if (messageStatus.name().equalsIgnoreCase(status))
            {
                return messageStatus;
            }
        }
        throw new InvalidArgumentException(String.format(INVALID_ENUM_MESSAGE, status));
    }
}
