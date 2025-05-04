package dev.dankov.primes.dto.response;

import dev.dankov.primes.dto.UserKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.UUID_MESSAGE;

@Schema(description = "User response")
public class UserResponseDto extends UserKey
{
    @NotNull(message = UUID_MESSAGE)
    @Valid
    @Schema(example = "00000000-0000-0000-0000-000000000000")
    private UUID id;

    public UserResponseDto()
    {
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof UserResponseDto))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        UserResponseDto that = (UserResponseDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString()
    {
        return "UserResponseDto{" +
            "id=" + id +
            "} " + super.toString();
    }
}
