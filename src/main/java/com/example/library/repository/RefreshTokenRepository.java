package com.example.library.repository;
import com.example.library.entity.RefreshToken;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findByUserIdAndRevokedFalse(Long userId);
}