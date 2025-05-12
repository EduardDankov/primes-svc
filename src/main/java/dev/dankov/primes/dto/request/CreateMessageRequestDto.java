package dev.dankov.primes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;
import java.util.UUID;

import static dev.dankov.primes.config.Constants.*;

public class CreateMessageRequestDto
{
    @NotNull(message = UUID_MESSAGE)
    @Valid
    @Schema(example = "00000000-0000-0000-0000-000000000000")
    private UUID chatId;

    @NotBlank(message = MESSAGE_TEXT_MESSAGE)
    @Valid
    @Length(max = MESSAGE_MAX_LENGTH, message = MESSAGE_LENGTH_MESSAGE)
    private String message;

    public CreateMessageRequestDto()
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

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof CreateMessageRequestDto))
        {
            return false;
        }
        CreateMessageRequestDto that = (CreateMessageRequestDto) o;
        return Objects.equals(chatId, that.chatId) &&
            Objects.equals(message, that.message);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(chatId, message);
    }

    @Override
    public String toString()
    {
        return "CreateMessageRequestDto{" +
            "chatId=" + chatId +
            ", message='" + message + '\'' +
            '}';
    }
}
