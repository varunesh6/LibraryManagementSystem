package com.example.librarymangementsystem.DTO;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "Token is Required")
    private String Token;

    @NotBlank(message = "new password is required")
    @Size(min = 8,max = 20)
    private String newPassword;
}
