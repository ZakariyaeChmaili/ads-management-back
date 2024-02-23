package com.virtuocode.adsmanagementback.controllers;


import com.virtuocode.adsmanagementback.dto.LoginRequestDto;
import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.services.authService.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    ResponseEntity login(@RequestBody @Valid LoginRequestDto login) {

        String token = this.authService.login(login);
        Map<String,Object> response = Map.of("token", token);
        return ResponseEntity.status(200).body(response);
    }


    @PostMapping("/register")
    ResponseEntity register(@RequestBody @Valid LoginRequestDto login) {

        User user = User.builder()
                .username(login.getUsername())
                .password(login.getPassword())
                .build();
        this.authService.register(user);
        return ResponseEntity.status(200).body("User registered successfully");
    }


}
