package com.example.librarymangementsystem.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Boolean available = true;

    @OneToMany(mappedBy = "book")
    private List<BorrowTransaction> borrowTransactions = new ArrayList<>();
}
