package dev.dankov.primes.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter
{
    private final PrimesAuthenticationProvider authenticationProvider;

    public TokenAuthenticationFilter(PrimesAuthenticationProvider authenticationProvider)
    {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer "))
        {
            token = token.substring(7);

            String[] tokenParts = token.split(":");

            if (tokenParts.length != 2)
            {
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token format");
                return;
            }

            Authentication auth = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(tokenParts[0], tokenParts[1]));

            if (auth.isAuthenticated())
            {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
