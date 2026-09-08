package com.example.librarymangementsystem.Repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshToken extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String Token);
    List<RefreshToken> findByuser(User user);
    void deleteByUser(User user);
}
