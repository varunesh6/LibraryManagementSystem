package com.example.librarymangementsystem.Repository;

import com.example.librarymangementsystem.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
}
