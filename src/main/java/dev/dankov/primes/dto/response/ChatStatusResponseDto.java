package dev.dankov.primes.dto.response;

import dev.dankov.primes.enums.ChatStatus;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ChatStatusResponseDto extends ChatResponseDto
{
    @NotNull
    private ChatStatus status;

    public ChatStatusResponseDto()
    {
    }

    public ChatStatus getStatus()
    {
        return status;
    }

    public void setStatus(ChatStatus status)
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
        if (!(o instanceof ChatStatusResponseDto))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        ChatStatusResponseDto that = (ChatStatusResponseDto) o;
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
        return "ChatStatusResponseDto{" +
            "status=" + status +
            "} " + super.toString();
    }
}
