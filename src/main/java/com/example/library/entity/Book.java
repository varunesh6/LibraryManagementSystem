package com.example.library.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="books")
public class Book {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String title;
    @Column(nullable=false) private String author;
    @Column(nullable=false) private String genre;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal amount;
    @Column(nullable=false) private boolean available=true;

    @OneToMany(mappedBy="book")
    private List<BorrowTransaction> borrowTransactions=new ArrayList<>();

    public Book(){}
    public Book(String title,String author,String genre,BigDecimal amount,boolean available){
        this.title=title;this.author=author;this.genre=genre;this.amount=amount;this.available=available;
    }
    public Long getId(){return id;} public String getTitle(){return title;} public String getAuthor(){return author;}
    public String getGenre(){return genre;} public BigDecimal getAmount(){return amount;} public boolean isAvailable(){return available;}
    public void setTitle(String v){title=v;} public void setAuthor(String v){author=v;} public void setGenre(String v){genre=v;}
    public void setAmount(BigDecimal v){amount=v;} public void setAvailable(boolean v){available=v;}
}