package com.example.librarymangementsystem.DTO;


import com.example.librarymangementsystem.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Provider;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String Name;
    private String Email;
    private Provider provider;
    private Role role;
}
