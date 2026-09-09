package com.example.library.dto;
import com.example.library.entity.BorrowStatus;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record AdminBorrowUpdateRequest(@NotNull BigDecimal amount,@NotNull @PositiveOrZero BigDecimal fine,@NotNull BorrowStatus status) {}