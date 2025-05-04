package dev.dankov.primes.dto.response;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;

public class ChatResponseDtoTest
{
    @Test
    public void validateBeanTest()
    {
        assertThat(ChatResponseDto.class, allOf(
            hasValidBeanConstructor(),
            hasValidGettersAndSetters(),
            hasValidBeanEquals(),
            hasValidBeanToString(),
            hasValidBeanHashCode()
        ));
    }
}
