package com.example.library.dto;
import java.math.BigDecimal;
public record BookResponse(Long id,String title,String author,String genre,BigDecimal amount,boolean available) {}