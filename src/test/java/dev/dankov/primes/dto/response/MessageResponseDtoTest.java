package dev.dankov.primes.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;

public class MessageResponseDtoTest
{
    @Test
    public void validateBeanTest()
    {
        registerValueGenerator(this::generateLocalDateTime, LocalDateTime.class);
        assertThat(MessageResponseDto.class, allOf(
            hasValidBeanConstructor(),
            hasValidGettersAndSetters(),
            hasValidBeanEquals(),
            hasValidBeanToString(),
            hasValidBeanHashCode()
        ));
    }

    private LocalDateTime generateLocalDateTime()
    {
        return LocalDateTime.now();
    }
}
