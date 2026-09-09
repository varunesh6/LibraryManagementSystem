package com.example.library.security;
import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository users;
    public CustomUserDetailsService(UserRepository users){this.users=users;}
    @Override @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u=users.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return User.withUsername(u.getEmail()).password(u.getPassword()==null?"":u.getPassword()).roles(u.getRole().name()).build();
    }
}