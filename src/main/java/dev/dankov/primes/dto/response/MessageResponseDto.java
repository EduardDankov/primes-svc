package dev.dankov.primes.dto.response;

import dev.dankov.primes.dto.MessageKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.*;
import static dev.dankov.primes.config.Constants.MESSAGE_LENGTH_MESSAGE;

public class MessageResponseDto extends MessageKey
{
    @NotNull(message = UUID_MESSAGE)
    @Valid
    @Schema(example = "00000000-0000-0000-0000-000000000000")
    private UUID chatId;

    @NotNull(message = UUID_MESSAGE)
    @Valid
    @Schema(example = "00000000-0000-0000-0000-000000000000")
    private UUID userId;

    @NotBlank(message = MESSAGE_TEXT_MESSAGE)
    @Valid
    @Length(max = MESSAGE_MAX_LENGTH, message = MESSAGE_LENGTH_MESSAGE)
    private String message;

    @NotNull
    @Valid
    private LocalDateTime createdAt;

    public MessageResponseDto()
    {
    }

    public UUID getChatId()
    {
        return chatId;
    }

    public void setChatId(UUID chatId)
    {
        this.chatId = chatId;
    }

    public UUID getUserId()
    {
        return userId;
    }

    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof MessageResponseDto))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        MessageResponseDto that = (MessageResponseDto) o;
        return Objects.equals(chatId, that.chatId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(message, that.message) &&
            Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), chatId, userId, message, createdAt);
    }

    @Override
    public String toString()
    {
        return "MessageResponseDto{" +
            "chatId=" + chatId +
            ", userId=" + userId +
            ", message='" + message + '\'' +
            ", createdAt=" + createdAt +
            '}' + super.toString();
    }
}
