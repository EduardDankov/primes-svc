package dev.dankov.primes.dto.response;

import dev.dankov.primes.dto.ChatKey;
import dev.dankov.primes.dto.request.CreateChatRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.UUID_MESSAGE;

public class ChatResponseDto extends ChatKey
{
    @NotNull(message = UUID_MESSAGE)
    @Valid
    @Schema(example = "00000000-0000-0000-0000-000000000000")
    private UUID createdBy;

    @NotNull(message = UUID_MESSAGE)
    @Valid
    @Schema(example = "00000000-0000-0000-0000-000000000000")
    private UUID createdWith;

    public ChatResponseDto()
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
        if (!(o instanceof ChatResponseDto))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        ChatResponseDto that = (ChatResponseDto) o;
        return Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdWith, that.createdWith);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), createdBy, createdWith);
    }

    @Override
    public String toString()
    {
        return "ChatResponseDto{" +
            "createdBy=" + createdBy +
            ", createdWith=" + createdWith +
            '}' + super.toString();
    }
}
