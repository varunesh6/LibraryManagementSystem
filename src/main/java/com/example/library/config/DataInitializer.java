package com.example.library.config;

import com.example.library.entity.*;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean CommandLineRunner createAdmin(UserRepository users,PasswordEncoder encoder,
        @Value("${app.bootstrap-admin.enabled:false}") boolean enabled,
        @Value("${app.bootstrap-admin.email:admin@college.local}") String email,
        @Value("${app.bootstrap-admin.password:ChangeMe123!}") String password) {
        return args -> {
            if(enabled && !users.existsByEmail(email.toLowerCase())){
                users.save(new User("Library Admin",email.toLowerCase(),encoder.encode(password),Provider.LOCAL,null,Role.ADMIN));
                System.out.println("Bootstrap admin created: "+email+" (change the password immediately)");
            }
        };
    }
}