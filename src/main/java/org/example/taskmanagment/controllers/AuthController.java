package org.example.taskmanagment.controllers;

import jakarta.validation.Valid;
import org.example.taskmanagment.dto.login.request.LoginRequest;
import org.example.taskmanagment.services.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Authentication loginUser(@Valid @RequestBody LoginRequest loginDetails) {
        return authService.loginUser(loginDetails);
    }
}
