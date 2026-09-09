package com.example.library.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper=new ObjectMapper();
    public void commence(HttpServletRequest req,HttpServletResponse res,AuthenticationException ex)throws IOException{
        res.setStatus(HttpStatus.UNAUTHORIZED.value());res.setContentType("application/json");
        mapper.writeValue(res.getOutputStream(),Map.of("timestamp",LocalDateTime.now(),"status",401,"error","Unauthorized","message","Authentication required"));
    }
}