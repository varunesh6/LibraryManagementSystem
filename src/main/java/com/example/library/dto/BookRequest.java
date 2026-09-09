package com.example.library.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record BookRequest(@NotBlank @Size(max=200) String title,@NotBlank @Size(max=150) String author,@NotBlank @Size(max=100) String genre,@NotNull @Positive BigDecimal amount,@NotNull Boolean available) {}