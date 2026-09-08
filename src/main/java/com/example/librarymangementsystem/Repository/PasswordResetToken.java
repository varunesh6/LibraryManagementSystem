package com.example.librarymangementsystem.Repository;

import com.example.librarymangementsystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetToken extends JpaRepository<PasswordResetToken,Long> {

    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(User user);

}
