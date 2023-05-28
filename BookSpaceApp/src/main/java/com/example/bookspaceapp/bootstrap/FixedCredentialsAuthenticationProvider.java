package com.example.bookspaceapp.bootstrap;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class FixedCredentialsAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Get the provided username and password
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Provide the fixed credentials for authentication
        String fixedUsername = "reader";
        String fixedPassword = "12345";

        // Check if the provided credentials match the fixed credentials
        if (username.equals(fixedUsername) && password.equals(fixedPassword)) {
            return new UsernamePasswordAuthenticationToken(username, password);
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
