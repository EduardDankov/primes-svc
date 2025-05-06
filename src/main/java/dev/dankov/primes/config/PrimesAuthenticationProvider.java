package dev.dankov.primes.config;

import dev.dankov.primes.dao.UserRepository;
import dev.dankov.primes.entity.UserEntity;
import dev.dankov.primes.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static dev.dankov.primes.config.Constants.USER_NOT_FOUND_MESSAGE;

@Component
public class PrimesAuthenticationProvider implements AuthenticationProvider
{
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public PrimesAuthenticationProvider(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Override
    public Authentication authenticate(final Authentication authentication)
    {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        return authenticateAgainstDatabase(username, password);
    }

    private Authentication authenticateAgainstDatabase(String username, String password)
    {
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND_MESSAGE));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password, List.of());
        auth.setDetails(user);
        if (!encoder.matches(password, user.getPassword()))
        {
            auth.setAuthenticated(false);
        }
        return auth;
    }
}
