package com.example.librarymangementsystem.DTO;


import com.example.librarymangementsystem.Entity.BorrowStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminBorrowUpdateRequest {
    @PositiveOrZero(message = "Amount cannot be negative")
    private BigDecimal amount;
    @PositiveOrZero(message = "Amount cannot be negative")
    private BigDecimal fine;
    @NotBlank(message = "Status is required")
    private BorrowStatus status;
}
