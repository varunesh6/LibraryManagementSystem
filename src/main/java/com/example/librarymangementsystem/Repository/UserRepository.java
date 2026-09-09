package com.example.librarymangementsystem.Repository;

import com.example.librarymangementsystem.Entity.Provider;
import com.example.librarymangementsystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

    Optional<User> findByProviderandProviderId(Provider provider,String providerid);
}
