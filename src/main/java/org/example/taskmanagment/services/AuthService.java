package org.example.taskmanagment.services;


import org.example.taskmanagment.dto.login.request.LoginRequest;
import org.example.taskmanagment.exceptions.InvalidCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String loginUser(LoginRequest loginDetails) {
        try {
        Authentication authentication =  authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginDetails.getEmail(),
                        loginDetails.getPassword()));
        return jwtService.createToken(authentication);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("User with invalid credentials" + e);
        }
    }
}
