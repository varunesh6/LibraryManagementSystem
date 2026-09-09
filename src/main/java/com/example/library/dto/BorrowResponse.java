package com.example.library.dto;
import com.example.library.entity.BorrowStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public record BorrowResponse(Long id,Long userId,String userName,Long bookId,String bookTitle,LocalDateTime borrowedAt,LocalDateTime dueAt,LocalDateTime returnedAt,BigDecimal amount,BigDecimal fine,BorrowStatus status) {}