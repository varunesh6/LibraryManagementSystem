package com.example.library.dto;
import com.example.library.entity.*;
public record UserResponse(Long id,String name,String email,Provider provider,Role role) {}