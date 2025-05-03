package dev.dankov.primes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

import static dev.dankov.primes.config.Constants.*;

public class UserKey
{
    @NotBlank(message = USERNAME_MESSAGE)
    @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_MESSAGE)
    @Length(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = USERNAME_LENGTH_MESSAGE)
    @Schema(example = "username")
    private String username;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof UserKey))
        {
            return false;
        }
        UserKey userKey = (UserKey) o;
        return Objects.equals(username, userKey.username);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username);
    }

    @Override
    public String toString()
    {
        return "UserKey{" +
            "username='" + username + '\'' +
            '}';
    }
}
