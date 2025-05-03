package dev.dankov.primes.enums;

import dev.dankov.primes.exception.InvalidArgumentException;

import static dev.dankov.primes.config.Constants.INVALID_ENUM_MESSAGE;

public enum UserStatus
{
    CREATED("CREATED"),
    AUTHENTICATED("AUTHENTICATED"),
    UPDATED("UPDATED"),
    DELETED("DELETED"),
    FAILED("FAILED");

    private final String status;

    UserStatus(String status)
    {
        this.status = status;
    }

    public static UserStatus fromString(String status)
    {
        for (UserStatus userStatus : UserStatus.values())
        {
            if (userStatus.name().equalsIgnoreCase(status))
            {
                return userStatus;
            }
        }
        throw new InvalidArgumentException(String.format(INVALID_ENUM_MESSAGE, status));
    }
}
