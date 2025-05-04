package dev.dankov.primes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.UUID_MESSAGE;

public class ChatKey
{
    @NotNull(message = UUID_MESSAGE)
    @Valid
    @Schema(example = "00000000-0000-0000-0000-000000000000")
    private UUID id;

    public ChatKey()
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
        if (!(o instanceof ChatKey))
        {
            return false;
        }
        ChatKey chatKey = (ChatKey) o;
        return Objects.equals(id, chatKey.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString()
    {
        return "ChatKey{" +
            "id=" + id +
            '}';
    }
}
