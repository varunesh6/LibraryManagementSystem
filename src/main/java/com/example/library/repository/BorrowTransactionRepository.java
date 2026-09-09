package com.example.library.repository;
import com.example.library.entity.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction,Long>{
    List<BorrowTransaction> findByUserIdOrderByBorrowedAtDesc(Long userId);
    List<BorrowTransaction> findByUserIdAndStatusInOrderByBorrowedAtDesc(Long userId,List<BorrowStatus> statuses);
    Optional<BorrowTransaction> findByIdAndUserId(Long id,Long userId);
    List<BorrowTransaction> findByStatusAndDueAtBefore(BorrowStatus status, java.time.LocalDateTime dueAt);
    @Query("select b from BorrowTransaction b where b.status in :statuses order by b.dueAt asc")
    List<BorrowTransaction> findByStatuses(@Param("statuses") List<BorrowStatus> statuses);
    @Query("select b from BorrowTransaction b where b.fine > 0 order by b.fine desc")
    List<BorrowTransaction> findWithFines();
}