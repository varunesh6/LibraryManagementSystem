package com.example.library.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="borrow_transactions")
public class BorrowTransaction {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id",nullable=false) private User user;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="book_id",nullable=false) private Book book;
    @Column(nullable=false) private LocalDateTime borrowedAt;
    @Column(nullable=false) private LocalDateTime dueAt;
    private LocalDateTime returnedAt;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal amount;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal fine;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private BorrowStatus status;

    public BorrowTransaction(){}
    public Long getId(){return id;} public User getUser(){return user;} public Book getBook(){return book;}
    public LocalDateTime getBorrowedAt(){return borrowedAt;} public LocalDateTime getDueAt(){return dueAt;}
    public LocalDateTime getReturnedAt(){return returnedAt;} public BigDecimal getAmount(){return amount;} public BigDecimal getFine(){return fine;}
    public BorrowStatus getStatus(){return status;}
    public void setUser(User v){user=v;} public void setBook(Book v){book=v;} public void setBorrowedAt(LocalDateTime v){borrowedAt=v;}
    public void setDueAt(LocalDateTime v){dueAt=v;} public void setReturnedAt(LocalDateTime v){returnedAt=v;} public void setAmount(BigDecimal v){amount=v;}
    public void setFine(BigDecimal v){fine=v;} public void setStatus(BorrowStatus v){status=v;}
}