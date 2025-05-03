package dev.dankov.primes.dto.request;

import dev.dankov.primes.dto.UserKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

import static dev.dankov.primes.config.Constants.PASSWORD_MESSAGE;

@Schema(description = "Create user request")
public class CreateUserRequestDto extends UserKey
{
    @NotBlank(message = PASSWORD_MESSAGE)
    @Schema(example = "password")
    private String password;

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof CreateUserRequestDto))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        CreateUserRequestDto that = (CreateUserRequestDto) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), password);
    }

    @Override
    public String toString()
    {
        return "CreateUserRequestDto{" +
            "password='" + password + '\'' +
            '}' + super.toString();
    }
}
