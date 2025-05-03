package dev.dankov.primes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(requests -> requests
            .requestMatchers(HttpMethod.POST, "/v1/user/authorization")
            .permitAll()
            .requestMatchers(HttpMethod.POST, "/v1/user")
            .permitAll()
            .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/webjars/**", "/swagger-resources/**", "/v3/api-docs/**", "/swagger.json")
            .permitAll()
            .anyRequest()
            .authenticated());
        return http.build();
    }
}
