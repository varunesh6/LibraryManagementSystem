package com.example.librarymangementsystem.DTO;

import com.example.librarymangementsystem.Entity.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
public class BorrowResponse {
    private Long id;

    private Long userId;

    private String userName;

    private Long bookId;

    private String bookTitle;

    private LocalDateTime borrowedAt;

    private LocalDateTime dueAt;

    private LocalDateTime returnedAt;

    private BigDecimal amount;

    private BigDecimal fine;

    private BorrowStatus status;

}
