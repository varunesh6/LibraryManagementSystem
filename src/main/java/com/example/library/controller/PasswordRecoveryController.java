package com.example.library.controller;

import com.example.library.dto.*;
import com.example.library.service.PasswordRecoveryService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordRecoveryController {
    private final PasswordRecoveryService service;
    public PasswordRecoveryController(PasswordRecoveryService service){this.service=service;}
    @PostMapping("/forgot-password")
    public Map<String,String> forgot(@Valid @RequestBody ForgotPasswordRequest r){
        String token=service.forgot(r.email());
        return Map.of("message","Password reset token generated. In production send it by email.","resetToken",token);
    }
    @PostMapping("/reset-password")
    public Map<String,String> reset(@Valid @RequestBody ResetPasswordRequest r){
        service.reset(r.token(),r.newPassword()); return Map.of("message","Password reset successful");
    }
}