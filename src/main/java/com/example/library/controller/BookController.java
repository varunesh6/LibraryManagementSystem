package com.example.library.controller;
import com.example.library.dto.BookResponse;
import com.example.library.service.BookService;
import java.util.List;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;
    public BookController(BookService service){this.service=service;}
    @GetMapping public List<BookResponse> all(){return service.findAll();}
    @GetMapping("/{id}") public BookResponse one(@PathVariable Long id){return service.findById(id);}
}