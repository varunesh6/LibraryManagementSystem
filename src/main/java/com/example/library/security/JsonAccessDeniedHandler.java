package com.example.library.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper mapper=new ObjectMapper();
    public void handle(HttpServletRequest req,HttpServletResponse res,AccessDeniedException ex)throws IOException{
        res.setStatus(HttpStatus.FORBIDDEN.value());res.setContentType("application/json");
        mapper.writeValue(res.getOutputStream(),Map.of("timestamp",LocalDateTime.now(),"status",403,"error","Forbidden","message","Access denied"));
    }
}