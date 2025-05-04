package dev.dankov.primes.dto.request;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;

public class CreateUserRequestDtoTest
{
     @Test
     public void validateBeanTest()
     {
         assertThat(CreateUserRequestDto.class, allOf(
             hasValidBeanConstructor(),
             hasValidGettersAndSetters(),
             hasValidBeanEquals(),
             hasValidBeanToString(),
             hasValidBeanHashCode()
         ));
     }
}
