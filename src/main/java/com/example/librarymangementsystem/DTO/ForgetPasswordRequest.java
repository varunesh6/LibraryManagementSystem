package com.example.librarymangementsystem.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invaild Email")
    private String email;
}
