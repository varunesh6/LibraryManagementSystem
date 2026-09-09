package com.example.librarymangementsystem.Repository;

import com.example.librarymangementsystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenRepository,Long> {

    Optional<PasswordResetTokenRepository> findByToken(String token);
    void deleteByUser(User user);

}
