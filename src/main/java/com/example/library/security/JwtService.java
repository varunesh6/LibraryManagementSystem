package com.example.library.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final Key key;
    private final long expirationMs;
    public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expirationMs){
        if(secret.length()<32) throw new IllegalArgumentException("JWT secret must be at least 32 characters");
        this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expirationMs=expirationMs;
    }
    public String generateAccessToken(String email){
        Instant now=Instant.now();
        return Jwts.builder().subject(email).issuedAt(Date.from(now)).expiration(Date.from(now.plusMillis(expirationMs)))
            .signWith(key).compact();
    }
    public String extractUsername(String token){return parse(token).getPayload().getSubject();}
    public boolean isValid(String token){
        try { parse(token); return true; } catch(JwtException|IllegalArgumentException e){return false;}
    }
    public long getExpirationSeconds(){return expirationMs/1000;}
    private Jws<Claims> parse(String token){return Jwts.parser().verifyWith((javax.crypto.SecretKey)key).build().parseSignedClaims(token);}
}