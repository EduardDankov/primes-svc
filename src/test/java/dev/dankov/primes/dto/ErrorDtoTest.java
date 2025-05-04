package dev.dankov.primes.dto;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;

public class ErrorDtoTest
{
    @Test
    public void validateBeanTest()
    {
        assertThat(ErrorDto.class, allOf(
            hasValidBeanConstructor(),
            hasValidGettersAndSetters(),
            hasValidBeanEquals(),
            hasValidBeanToString(),
            hasValidBeanHashCode()
        ));
    }
}
