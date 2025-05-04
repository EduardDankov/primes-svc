package dev.dankov.primes.dto;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;

public class ChatKeyTest
{
    @Test
    public void validateBeanTest()
    {
        assertThat(ChatKey.class, allOf(
            hasValidBeanConstructor(),
            hasValidGettersAndSetters(),
            hasValidBeanEquals(),
            hasValidBeanToString(),
            hasValidBeanHashCode()
        ));
    }
}
