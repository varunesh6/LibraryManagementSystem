package com.example.library.repository;
import com.example.library.entity.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface BookRepository extends JpaRepository<Book,Long>{
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Book b where b.id=:id")
    Optional<Book> findByIdForUpdate(@Param("id") Long id);
}