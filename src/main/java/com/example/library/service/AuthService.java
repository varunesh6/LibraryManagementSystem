package com.example.library.service;

import com.example.library.dto.*;
import com.example.library.entity.*;
import com.example.library.exception.BadRequestException;
import com.example.library.repository.UserRepository;
import com.example.library.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository users; private final PasswordEncoder encoder; private final AuthenticationManager authManager;
    private final JwtService jwt; private final RefreshTokenService refresh;
    public AuthService(UserRepository users,PasswordEncoder encoder,AuthenticationManager authManager,JwtService jwt,RefreshTokenService refresh){
        this.users=users;this.encoder=encoder;this.authManager=authManager;this.jwt=jwt;this.refresh=refresh;
    }
    @Transactional public void register(RegisterRequest req){
        String email=req.email().trim().toLowerCase();
        if(users.existsByEmail(email))throw new BadRequestException("Email already registered");
        users.save(new User(req.name().trim(),email,encoder.encode(req.password()),Provider.LOCAL,null,Role.USER));
    }
    @Transactional public AuthResponse login(LoginRequest req){
        String email=req.email().trim().toLowerCase();
        authManager.authenticate(new UsernamePasswordAuthenticationToken(email,req.password()));
        User user=users.findByEmail(email).orElseThrow(()->new BadRequestException("Invalid credentials"));
        return tokens(user);
    }
    @Transactional public AuthResponse tokens(User user){
        RefreshToken rt=refresh.createRefreshToken(user);
        return new AuthResponse(jwt.generateAccessToken(user.getEmail()),rt.getToken(),"Bearer",jwt.getExpirationSeconds());
    }
    @Transactional public AuthResponse refresh(String token){
        RefreshToken old=refresh.verifyRefreshToken(token); User user=old.getUser(); refresh.rotateRefreshToken(old); return tokens(user);
    }
    @Transactional public void logout(String token){refresh.revokeRefreshToken(token);}
}