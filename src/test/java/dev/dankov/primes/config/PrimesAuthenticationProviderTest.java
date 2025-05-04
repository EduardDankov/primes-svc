package dev.dankov.primes.config;

import dev.dankov.primes.dao.UserRepository;
import dev.dankov.primes.entity.UserEntity;
import dev.dankov.primes.exception.AuthenticationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static dev.dankov.primes.config.Constants.USER_NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrimesAuthenticationProviderTest
{
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String PASSWORD2 = "password2";

    private PrimesAuthenticationProvider provider;
    private UserRepository userRepository;
    private Authentication authentication;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeAll
    public void setup()
    {
        userRepository = mock(UserRepository.class);
        authentication = mock(Authentication.class);
        provider = new PrimesAuthenticationProvider(userRepository);
    }

    @Test
    public void authenticateAgainstDatabaseTestValid()
    {
        UserEntity expectedUser = new UserEntity();
        expectedUser.setUsername(USERNAME);
        expectedUser.setPassword(encoder.encode(PASSWORD));

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(expectedUser));
        when(authentication.getName()).thenReturn(USERNAME);
        when(authentication.getCredentials()).thenReturn(PASSWORD);

        Authentication result = provider.authenticate(authentication);
        assertTrue(result.isAuthenticated());
    }

    @Test
    public void authenticateAgainstDatabaseTestInvalidUser()
    {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(authentication.getName()).thenReturn(USERNAME);
        when(authentication.getCredentials()).thenReturn(PASSWORD);

        try
        {
            provider.authenticate(authentication);
            fail("Expected AuthenticationException to be thrown");
        } catch (AuthenticationException e)
        {
            assertEquals(USER_NOT_FOUND_MESSAGE, e.getMessage());
        }
    }

    @Test
    public void authenticateAgainstDatabaseTestInvalidCredentials()
    {
        UserEntity expectedUser = new UserEntity();
        expectedUser.setUsername(USERNAME);
        expectedUser.setPassword(encoder.encode(PASSWORD2));

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(expectedUser));
        when(authentication.getName()).thenReturn(USERNAME);
        when(authentication.getCredentials()).thenReturn(PASSWORD);

        Authentication result = provider.authenticate(authentication);
        assertFalse(result.isAuthenticated());
    }
}
