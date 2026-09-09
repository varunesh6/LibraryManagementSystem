package com.example.library.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService; private final CustomUserDetailsService userDetailsService;
    public JwtAuthenticationFilter(JwtService jwtService,CustomUserDetailsService uds){this.jwtService=jwtService;this.userDetailsService=uds;}
    @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)throws ServletException,IOException{
        String header=request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header!=null && header.startsWith("Bearer ")){
            String token=header.substring(7);
            if(jwtService.isValid(token) && SecurityContextHolder.getContext().getAuthentication()==null){
                String email=jwtService.extractUsername(token);
                try {
                    UserDetails details=userDetailsService.loadUserByUsername(email);
                    var auth=new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } catch(Exception ignored){}
            }
        }
        filterChain.doFilter(request,response);
    }
}