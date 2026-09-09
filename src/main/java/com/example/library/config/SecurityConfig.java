package com.example.library.config;

import com.example.library.security.*;
import com.example.library.service.OAuth2SuccessHandler;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtFilter;
    private final JsonAuthenticationEntryPoint entryPoint;
    private final JsonAccessDeniedHandler deniedHandler;
    private final OAuth2SuccessHandler oauth2SuccessHandler;
    public SecurityConfig(JwtAuthenticationFilter jwtFilter,JsonAuthenticationEntryPoint entryPoint,JsonAccessDeniedHandler deniedHandler,OAuth2SuccessHandler oauth2SuccessHandler){
        this.jwtFilter=jwtFilter;this.entryPoint=entryPoint;this.deniedHandler=deniedHandler;this.oauth2SuccessHandler=oauth2SuccessHandler;
    }
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration c)throws Exception{return c.getAuthenticationManager();}
    @Bean SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.csrf(csrf->csrf.disable())
          .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .exceptionHandling(e->e.authenticationEntryPoint(entryPoint).accessDeniedHandler(deniedHandler))
          .authorizeHttpRequests(a->a
              .requestMatchers("/api/auth/register","/api/auth/login","/api/auth/refresh","/api/auth/forgot-password","/api/auth/reset-password").permitAll()
              .requestMatchers("/oauth2/**","/login/**").permitAll()
              .requestMatchers("/api/admin/**").hasRole("ADMIN")
              .requestMatchers("/api/books/**","/api/borrow/**").hasAnyRole("USER","ADMIN")
              .anyRequest().authenticated())
          .oauth2Login(o->o.successHandler(oauth2SuccessHandler))
          .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}