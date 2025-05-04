package dev.dankov.primes.dto.response;

import dev.dankov.primes.enums.MessageStatus;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class MessageStatusResponseDto extends MessageResponseDto
{
    @NotNull
    private MessageStatus status;

    public MessageStatusResponseDto()
    {
    }

    public MessageStatus getStatus()
    {
        return status;
    }

    public void setStatus(MessageStatus status)
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
        if (!(o instanceof MessageStatusResponseDto))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        MessageStatusResponseDto that = (MessageStatusResponseDto) o;
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
        return "MessageStatusResponseDto{" +
            "status=" + status +
            "} " + super.toString();
    }
}
