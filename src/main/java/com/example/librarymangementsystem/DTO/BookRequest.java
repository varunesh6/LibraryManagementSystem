package com.example.librarymangementsystem.DTO;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "author is required")
    private String author;

    @NotBlank(message = "Genere is required")
    private String genere;

    @NotBlank(message = "Amount is required")
    @Positive(message = "Amount is positive")
    private BigDecimal amount;

}
