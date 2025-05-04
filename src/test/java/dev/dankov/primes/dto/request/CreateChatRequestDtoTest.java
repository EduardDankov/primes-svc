package dev.dankov.primes.dto.request;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;

public class CreateChatRequestDtoTest
{
     @Test
     public void validateBeanTest()
     {
         assertThat(CreateChatRequestDto.class, allOf(
             hasValidBeanConstructor(),
             hasValidGettersAndSetters(),
             hasValidBeanEquals(),
             hasValidBeanToString(),
             hasValidBeanHashCode()
         ));
     }
}
