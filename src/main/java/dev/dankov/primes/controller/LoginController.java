package dev.dankov.primes.controller;

import dev.dankov.primes.dto.request.CreateUserRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController
{
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/v1/login")
    public ResponseEntity<Void> login(
        @NonNull
        @Validated
        @RequestBody
        @Parameter(required = true)
        CreateUserRequestDto createUserRequestDto
    )
    {
        Authentication authenticationRequest =
            UsernamePasswordAuthenticationToken.unauthenticated(createUserRequestDto.getUsername(), createUserRequestDto.getPassword());
        Authentication authenticationResponse =
            this.authenticationManager.authenticate(authenticationRequest);

        if (!authenticationResponse.isAuthenticated())
        {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
