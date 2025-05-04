package dev.dankov.primes.dto.response;

import dev.dankov.primes.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Schema(description = "User status response")
public class UserStatusResponseDto extends UserResponseDto
{
    @NotNull
    private UserStatus status;

    public UserStatusResponseDto()
    {
    }

    public UserStatus getStatus()
    {
        return status;
    }

    public void setStatus(UserStatus status)
    {
        this.status = status;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof UserStatusResponseDto))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        UserStatusResponseDto that = (UserStatusResponseDto) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), status);
    }

    @Override
    public String toString()
    {
        return "UserStatusResponseDto{" +
            "status=" + status +
            "} " + super.toString();
    }
}
