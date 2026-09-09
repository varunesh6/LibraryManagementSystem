package com.example.library.controller;

import com.example.library.dto.BorrowResponse;
import com.example.library.service.BorrowService;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    private final BorrowService service;
    public BorrowController(BorrowService service){this.service=service;}
    @PostMapping("/book/{bookId}") public BorrowResponse borrow(@PathVariable Long bookId,Authentication a){return service.borrow(a.getName(),bookId);}
    @PutMapping("/{transactionId}/return") public BorrowResponse returnBook(@PathVariable Long transactionId,Authentication a){return service.returnBook(a.getName(),transactionId);}
    @GetMapping("/my-history") public List<BorrowResponse> history(Authentication a){return service.myHistory(a.getName());}
    @GetMapping("/my-active") public List<BorrowResponse> active(Authentication a){return service.myActive(a.getName());}
    @GetMapping("/my-fines") public List<BorrowResponse> fines(Authentication a){return service.myFines(a.getName());}
}