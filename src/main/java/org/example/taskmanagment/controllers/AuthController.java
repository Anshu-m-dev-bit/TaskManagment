package org.example.taskmanagment.controllers;

import jakarta.validation.Valid;
import org.example.taskmanagment.dto.login.LoginMapper;
import org.example.taskmanagment.dto.login.request.LoginRequest;
import org.example.taskmanagment.dto.login.response.LoginResponse;
import org.example.taskmanagment.services.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final LoginMapper loginMapper;

    public AuthController(AuthService authService, LoginMapper loginMapper) {
        this.authService = authService;
        this.loginMapper = loginMapper;
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@Valid @RequestBody LoginRequest loginDetails) {
        return loginMapper.toLoginResponse(loginDetails,
                "Login Successfully",
                authService.loginUser(loginDetails));
    }
}
