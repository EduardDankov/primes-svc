package dev.dankov.primes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dankov.primes.dto.request.CreateUserRequestDto;
import dev.dankov.primes.dto.response.UserStatusResponseDto;
import dev.dankov.primes.entity.UserEntity;
import dev.dankov.primes.enums.UserStatus;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
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
@Hidden
public class LoginController
{
    private final AuthenticationManager authenticationManager;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    public LoginController(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/v1/login")
    public ResponseEntity<UserStatusResponseDto> login(
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

        UserEntity user = MAPPER.convertValue(authenticationResponse.getDetails(), UserEntity.class);

        UserStatusResponseDto userStatusResponseDto = new UserStatusResponseDto();
        userStatusResponseDto.setStatus(UserStatus.AUTHENTICATED);
        userStatusResponseDto.setId(user.getId());
        userStatusResponseDto.setUsername(user.getUsername());

        return new ResponseEntity<>(userStatusResponseDto, HttpStatus.OK);
    }
}
