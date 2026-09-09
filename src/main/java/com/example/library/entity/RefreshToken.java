package com.example.library.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="refresh_tokens", indexes=@Index(name="idx_refresh_token", columnList="token"))
public class RefreshToken {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,unique=true,length=512) private String token;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id",nullable=false) private User user;
    @Column(nullable=false) private LocalDateTime expiryDate;
    @Column(nullable=false) private boolean revoked=false;
    @Column(nullable=false) private LocalDateTime createdAt;

    public RefreshToken(){}
    public Long getId(){return id;} public String getToken(){return token;} public User getUser(){return user;}
    public LocalDateTime getExpiryDate(){return expiryDate;} public boolean isRevoked(){return revoked;} public LocalDateTime getCreatedAt(){return createdAt;}
    public void setToken(String v){token=v;} public void setUser(User v){user=v;} public void setExpiryDate(LocalDateTime v){expiryDate=v;}
    public void setRevoked(boolean v){revoked=v;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}