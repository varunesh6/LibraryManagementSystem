package com.example.librarymangementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String genere;
    private BigDecimal amount;
    private boolean available;

}
