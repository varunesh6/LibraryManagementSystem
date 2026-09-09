package com.example.library.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users", uniqueConstraints=@UniqueConstraint(name="uk_users_email", columnNames="email"))
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String name;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=true) private String password;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Provider provider;
    private String providerId;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<BorrowTransaction> borrowTransactions = new ArrayList<>();
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<RefreshToken> refreshTokens = new ArrayList<>();
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<PasswordResetToken> passwordResetTokens = new ArrayList<>();

    public User() {}
    public User(String name,String email,String password,Provider provider,String providerId,Role role){
        this.name=name; this.email=email; this.password=password; this.provider=provider; this.providerId=providerId; this.role=role;
    }
    public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;}
    public String getPassword(){return password;} public Provider getProvider(){return provider;} public String getProviderId(){return providerId;}
    public Role getRole(){return role;} public void setName(String v){name=v;} public void setEmail(String v){email=v;}
    public void setPassword(String v){password=v;} public void setProvider(Provider v){provider=v;} public void setProviderId(String v){providerId=v;}
    public void setRole(Role v){role=v;}
}