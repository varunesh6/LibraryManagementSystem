package com.example.librarymangementsystem.Entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "UserName Cannot be Null")
    @Column(nullable = false)
    private String userName;

    @NotBlank(message = "Email Cannot be Null")
    @Column(nullable = false,unique = true)
    @Email
    private String userEmail;

    @NotBlank(message = "Invalid Password")
    @Column(nullable = false)
    @Size(min = 8)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<BorrowTransaction> borrowTransactions = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<RefreshToken> refreshTokens = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<PasswordResetToken> passwordResetTokens = new ArrayList<>();





}
