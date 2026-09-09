package com.example.librarymangementsystem.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message ="Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invaild Email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8,max = 1000)
    private String password;
}
