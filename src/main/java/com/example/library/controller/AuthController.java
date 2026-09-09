package com.example.library.controller;

import com.example.library.dto.*;
import com.example.library.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService auth;
    public AuthController(AuthService auth){this.auth=auth;}
    @PostMapping("/register") public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest r){auth.register(r);return ResponseEntity.status(HttpStatus.CREATED).build();}
    @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){return auth.login(r);}
    @PostMapping("/refresh") public AuthResponse refresh(@Valid @RequestBody RefreshTokenRequest r){return auth.refresh(r.refreshToken());}
    @PostMapping("/logout") public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest r){auth.logout(r.refreshToken());return ResponseEntity.noContent().build();}
}