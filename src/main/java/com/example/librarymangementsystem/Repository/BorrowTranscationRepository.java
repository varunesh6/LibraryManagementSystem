package com.example.librarymangementsystem.Repository;

import com.example.librarymangementsystem.Entity.BorrowStatus;
import com.example.librarymangementsystem.Entity.BorrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowTranscationRepository
        extends JpaRepository<BorrowTransaction, Long> {


    List<BorrowTransaction> findByUserIdOrderByBorrowedAtDesc(
            Long userId
    );


    List<BorrowTransaction> findByUserIdAndStatus(
            Long userId,
            BorrowStatus status
    );


    List<BorrowTransaction> findByUserIdAndFineGreaterThan(
            Long userId,
            java.math.BigDecimal fine
    );


    Optional<BorrowTransaction> findByIdAndUserId(
            Long id,
            Long userId
    );


    List<BorrowTransaction> findByStatus(
            BorrowStatus status
    );
}