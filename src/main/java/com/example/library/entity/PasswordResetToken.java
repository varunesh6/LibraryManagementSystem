package com.example.library.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="password_reset_tokens", indexes=@Index(name="idx_password_reset_token", columnList="token"))
public class PasswordResetToken {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,unique=true,length=512) private String token;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id",nullable=false) private User user;
    @Column(nullable=false) private LocalDateTime expiryDate;

    public PasswordResetToken(){}
    public Long getId(){return id;} public String getToken(){return token;} public User getUser(){return user;} public LocalDateTime getExpiryDate(){return expiryDate;}
    public void setToken(String v){token=v;} public void setUser(User v){user=v;} public void setExpiryDate(LocalDateTime v){expiryDate=v;}
}