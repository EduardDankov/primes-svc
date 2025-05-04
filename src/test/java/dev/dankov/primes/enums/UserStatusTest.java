package dev.dankov.primes.enums;

import org.junit.jupiter.api.Test;

import static dev.dankov.primes.config.Constants.INVALID_ENUM_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UserStatusTest
{
    @Test
    public void fromStringTestValid()
    {
        String status = "CREATED";
        UserStatus userStatus = UserStatus.fromString(status);
        assertEquals(UserStatus.CREATED, userStatus);
    }

    @Test
    public void fromStringTestInvalid()
    {
        String status = "INVALID_STATUS";
        try
        {
            UserStatus.fromString(status);
            fail("Expected IllegalArgumentException to be thrown");
        }
        catch (Exception e)
        {
            String expectedMessage = String.format(INVALID_ENUM_MESSAGE, status);
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
