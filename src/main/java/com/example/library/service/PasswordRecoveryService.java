package com.example.library.service;

import com.example.library.entity.*;
import com.example.library.exception.BadRequestException;
import com.example.library.repository.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordRecoveryService {
    private final UserRepository users; private final PasswordResetTokenRepository tokens; private final PasswordEncoder encoder; private final long expiryMinutes;
    public PasswordRecoveryService(UserRepository users,PasswordResetTokenRepository tokens,PasswordEncoder encoder,@Value("${app.password-reset.expiration-minutes}")long expiryMinutes){
        this.users=users;this.tokens=tokens;this.encoder=encoder;this.expiryMinutes=expiryMinutes;
    }
    @Transactional public String forgot(String email){
        User user=users.findByEmail(email.trim().toLowerCase()).orElseThrow(()->new BadRequestException("No account exists for this email"));
        PasswordResetToken t=new PasswordResetToken();t.setUser(user);t.setToken(UUID.randomUUID().toString()+UUID.randomUUID());t.setExpiryDate(LocalDateTime.now().plusMinutes(expiryMinutes));tokens.save(t);
        return t.getToken(); // Development: send by email in production.
    }
    @Transactional public void reset(String token,String newPassword){
        PasswordResetToken t=tokens.findByToken(token).orElseThrow(()->new BadRequestException("Invalid reset token"));
        if(t.getExpiryDate().isBefore(LocalDateTime.now())){tokens.delete(t);throw new BadRequestException("Reset token has expired");}
        User u=t.getUser(); if(u.getProvider()==Provider.GOOGLE)throw new BadRequestException("Google accounts must reset password through Google");
        u.setPassword(encoder.encode(newPassword));users.save(u);tokens.delete(t);
    }
}