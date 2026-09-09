package com.example.library.controller;

import com.example.library.dto.*;
import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import com.example.library.service.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final BookService books; private final BorrowService borrow; private final UserRepository users;
    public AdminController(BookService books,BorrowService borrow,UserRepository users){this.books=books;this.borrow=borrow;this.users=users;}
    @PostMapping("/books") public BookResponse addBook(@Valid @RequestBody BookRequest r){return books.create(r);}
    @PutMapping("/books/{id}") public BookResponse updateBook(@PathVariable Long id,@Valid @RequestBody BookRequest r){return books.update(id,r);}
    @DeleteMapping("/books/{id}") public void deleteBook(@PathVariable Long id){books.delete(id);}
    @GetMapping("/users") public List<UserResponse> users(){return users.findAll().stream().map(u->new UserResponse(u.getId(),u.getName(),u.getEmail(),u.getProvider(),u.getRole())).toList();}
    @GetMapping("/users/{id}") public UserResponse user(@PathVariable Long id){User u=users.findById(id).orElseThrow(()->new com.example.library.exception.ResourceNotFoundException("User not found"));return new UserResponse(u.getId(),u.getName(),u.getEmail(),u.getProvider(),u.getRole());}
    @GetMapping("/borrowings") public List<BorrowResponse> borrowings(){return borrow.all();}
    @GetMapping("/borrowings/{id}") public BorrowResponse borrowing(@PathVariable Long id){return borrow.byId(id);}
    @GetMapping("/fines") public List<BorrowResponse> fines(){return borrow.fines();}
    @PutMapping("/borrowings/{id}") public BorrowResponse updateBorrowing(@PathVariable Long id,@Valid @RequestBody AdminBorrowUpdateRequest r){return borrow.adminUpdate(id,r);}
}