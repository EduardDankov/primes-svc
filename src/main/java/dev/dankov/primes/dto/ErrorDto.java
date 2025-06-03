package dev.dankov.primes.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

import static dev.dankov.primes.config.Constants.ERROR_MESSAGE;

public class ErrorDto
{
    @NotBlank(message = ERROR_MESSAGE)
    private String error;

    public ErrorDto()
    {
    }

    public ErrorDto(String error)
    {
        this.error = error;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ErrorDto))
        {
            return false;
        }
        ErrorDto errorDto = (ErrorDto) o;
        return !Objects.equals(error, errorDto.error);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(error);
    }

    @Override
    public String toString()
    {
        return "ErrorDto{" +
            "error='" + error + '\'' +
            '}';
    }
}
