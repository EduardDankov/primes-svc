package dev.dankov.primes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.UUID_MESSAGE;

public class CreateChatRequestDto
{
    @NotNull(message = UUID_MESSAGE)
    @Valid
    @Schema(example = "00000000-0000-0000-0000-000000000000")
    private UUID createdBy;

    @NotNull(message = UUID_MESSAGE)
    @Valid
    @Schema(example = "00000000-0000-0000-0000-000000000000")
    private UUID createdWith;

    public CreateChatRequestDto()
    {
    }

    public UUID getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy)
    {
        this.createdBy = createdBy;
    }

    public UUID getCreatedWith()
    {
        return createdWith;
    }

    public void setCreatedWith(UUID createdWith)
    {
        this.createdWith = createdWith;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof CreateChatRequestDto))
        {
            return false;
        }
        CreateChatRequestDto that = (CreateChatRequestDto) o;
        return Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdWith, that.createdWith);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(createdBy, createdWith);
    }

    @Override
    public String toString()
    {
        return "CreateChatRequestDto{" +
            "createdBy=" + createdBy +
            ", createdWith=" + createdWith +
            '}';
    }
}
