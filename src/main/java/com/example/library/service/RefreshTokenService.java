package com.example.library.service;

import com.example.library.entity.*;
import com.example.library.exception.BadRequestException;
import com.example.library.repository.RefreshTokenRepository;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository repo; private final long expiryDays;
    public RefreshTokenService(RefreshTokenRepository repo,@Value("${app.refresh-token.expiration-days}") long expiryDays){this.repo=repo;this.expiryDays=expiryDays;}
    @Transactional public RefreshToken createRefreshToken(User user){
        RefreshToken t=new RefreshToken();t.setToken(UUID.randomUUID()+"."+UUID.randomUUID());t.setUser(user);
        t.setCreatedAt(LocalDateTime.now());t.setExpiryDate(LocalDateTime.now().plusDays(expiryDays));t.setRevoked(false);return repo.save(t);
    }
    @Transactional(readOnly=true) public RefreshToken verifyRefreshToken(String token){
        RefreshToken t=repo.findByToken(token).orElseThrow(()->new BadRequestException("Invalid refresh token"));
        if(t.isRevoked())throw new BadRequestException("Refresh token has been revoked");
        if(t.getExpiryDate().isBefore(LocalDateTime.now()))throw new BadRequestException("Refresh token has expired");
        return t;
    }
    @Transactional public void rotateRefreshToken(RefreshToken old){old.setRevoked(true);repo.save(old);}
    @Transactional public void revokeRefreshToken(String token){repo.findByToken(token).ifPresent(t->{t.setRevoked(true);repo.save(t);});}
    @Transactional public void revokeAllUserTokens(User user){repo.findByUserIdAndRevokedFalse(user.getId()).forEach(t->{t.setRevoked(true);repo.save(t);});}
}