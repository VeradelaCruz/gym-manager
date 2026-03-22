package com.gym.auth_service.controller;

import com.gym.auth_service.dto.LoginRequest;
import com.gym.auth_service.dto.RegisterRequest;
import com.gym.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// requests HTTP

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request){
        authService.register(
                request.getUsername(),
                request.getPassword(),
                request.getRoles(),
                request.getMemberId()
        );
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        return authService.authenticate(
                request.getUsername(),
                request.getPassword()
        );
    }
}