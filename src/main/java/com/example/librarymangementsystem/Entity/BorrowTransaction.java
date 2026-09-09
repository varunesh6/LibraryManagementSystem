package com.example.librarymangementsystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "borrow_transcation")
@AllArgsConstructor
@NoArgsConstructor
public class BorrowTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Column(nullable = false,name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @Column(nullable = false,name = "book_id")
    private Book book;

    @Column(nullable = false)
    private LocalDateTime borrowedAt;

    @Column(nullable = false)
    private LocalDateTime dueAt;

    @Column(nullable = false)
    private LocalDateTime returnAt;

    @Column(nullable = false)
    private BigDecimal fine = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private BorrowStatus status;
}
