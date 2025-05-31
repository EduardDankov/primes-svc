package dev.dankov.primes.enums;

import dev.dankov.primes.exception.InvalidArgumentException;

import static dev.dankov.primes.config.Constants.INVALID_ENUM_MESSAGE;

public enum ChatStatus
{
    CREATED("CREATED"),
    DELETED("DELETED"),
    FAILED("FAILED");

    private final String status;

    ChatStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public static ChatStatus fromString(String status)
    {
        for (ChatStatus chatStatus : values())
        {
            if (chatStatus.name().equalsIgnoreCase(status))
            {
                return chatStatus;
            }
        }
        throw new InvalidArgumentException(String.format(INVALID_ENUM_MESSAGE, status));
    }
}
