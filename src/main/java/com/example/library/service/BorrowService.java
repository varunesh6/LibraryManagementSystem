package com.example.library.service;

import com.example.library.dto.*;
import com.example.library.entity.*;
import com.example.library.exception.*;
import com.example.library.repository.*;
import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class BorrowService {
    private static final BigDecimal FINE_PER_DAY=BigDecimal.TEN;
    private final BorrowTransactionRepository transactions; private final UserRepository users; private final BookService books;
    public BorrowService(BorrowTransactionRepository transactions,UserRepository users,BookService books){this.transactions=transactions;this.users=users;this.books=books;}

    @Transactional public BorrowResponse borrow(String email,Long bookId){
        User user=getUser(email); Book book=books.getEntityForBorrow(bookId);
        if(!book.isAvailable())throw new BadRequestException("Book is currently unavailable");
        LocalDateTime now=LocalDateTime.now();
        BorrowTransaction t=new BorrowTransaction();t.setUser(user);t.setBook(book);t.setBorrowedAt(now);t.setDueAt(now.plusDays(5));
        t.setAmount(book.getAmount());t.setFine(BigDecimal.ZERO);t.setStatus(BorrowStatus.BORROWED);book.setAvailable(false);
        return toResponse(transactions.save(t));
    }
    @Transactional public BorrowResponse returnBook(String email,Long transactionId){
        User user=getUser(email);
        BorrowTransaction t=transactions.findByIdAndUserId(transactionId,user.getId()).orElseThrow(()->new ResourceNotFoundException("Transaction not found for current user"));
        if(t.getStatus()==BorrowStatus.RETURNED)throw new BadRequestException("Book has already been returned");
        LocalDateTime now=LocalDateTime.now();t.setReturnedAt(now);
        long lateDays=now.isAfter(t.getDueAt())?ChronoUnit.DAYS.between(t.getDueAt().toLocalDate(),now.toLocalDate()):0;
        t.setFine(FINE_PER_DAY.multiply(BigDecimal.valueOf(lateDays)));t.setStatus(BorrowStatus.RETURNED);t.getBook().setAvailable(true);
        return toResponse(t);
    }
    @Transactional(readOnly=true) public List<BorrowResponse> myHistory(String email){return transactions.findByUserIdOrderByBorrowedAtDesc(getUser(email).getId()).stream().map(this::toResponse).toList();}
    @Transactional(readOnly=true) public List<BorrowResponse> myActive(String email){return transactions.findByUserIdAndStatusInOrderByBorrowedAtDesc(getUser(email).getId(),List.of(BorrowStatus.BORROWED,BorrowStatus.OVERDUE)).stream().map(this::toResponse).toList();}
    @Transactional(readOnly=true) public List<BorrowResponse> myFines(String email){return transactions.findByUserIdOrderByBorrowedAtDesc(getUser(email).getId()).stream().filter(t->t.getFine().compareTo(BigDecimal.ZERO)>0).map(this::toResponse).toList();}
    @Scheduled(fixedDelayString="${app.overdue-check-ms:60000}")
    @Transactional public void markOverdue(){transactions.findByStatusAndDueAtBefore(BorrowStatus.BORROWED,LocalDateTime.now()).forEach(t->t.setStatus(BorrowStatus.OVERDUE));}
    @Transactional(readOnly=true) public List<BorrowResponse> all(){return transactions.findAll().stream().map(this::toResponse).toList();}
    @Transactional(readOnly=true) public BorrowResponse byId(Long id){return toResponse(transactions.findById(id).orElseThrow(()->new ResourceNotFoundException("Transaction not found")));}
    @Transactional(readOnly=true) public List<BorrowResponse> fines(){return transactions.findWithFines().stream().map(this::toResponse).toList();}
    @Transactional public BorrowResponse adminUpdate(Long id,AdminBorrowUpdateRequest r){
        BorrowTransaction t=transactions.findById(id).orElseThrow(()->new ResourceNotFoundException("Transaction not found"));
        t.setAmount(r.amount());t.setFine(r.fine());t.setStatus(r.status());
        if(r.status()==BorrowStatus.RETURNED){t.getBook().setAvailable(true);if(t.getReturnedAt()==null)t.setReturnedAt(LocalDateTime.now());}
        else t.getBook().setAvailable(false);
        return toResponse(t);
    }
    private User getUser(String email){return users.findByEmail(email).orElseThrow(()->new UnauthorizedException("Authenticated user not found"));}
    private BorrowResponse toResponse(BorrowTransaction t){return new BorrowResponse(t.getId(),t.getUser().getId(),t.getUser().getName(),t.getBook().getId(),t.getBook().getTitle(),t.getBorrowedAt(),t.getDueAt(),t.getReturnedAt(),t.getAmount(),t.getFine(),t.getStatus());}
}