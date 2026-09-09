package com.example.library.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ApiError> error(HttpStatus status,String message,HttpServletRequest r){
        return ResponseEntity.status(status).body(new ApiError(LocalDateTime.now(),status.value(),status.getReasonPhrase(),message,r.getRequestURI()));
    }
    @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<ApiError> notFound(ResourceNotFoundException e,HttpServletRequest r){return error(HttpStatus.NOT_FOUND,e.getMessage(),r);}
    @ExceptionHandler({BadRequestException.class,ConstraintViolationException.class}) ResponseEntity<ApiError> bad(RuntimeException e,HttpServletRequest r){return error(HttpStatus.BAD_REQUEST,e.getMessage(),r);}
    @ExceptionHandler(UnauthorizedException.class) ResponseEntity<ApiError> unauth(UnauthorizedException e,HttpServletRequest r){return error(HttpStatus.UNAUTHORIZED,e.getMessage(),r);}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> validation(MethodArgumentNotValidException e,HttpServletRequest r){
        String msg=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).collect(Collectors.joining(", "));
        return error(HttpStatus.BAD_REQUEST,msg,r);
    }
    @ExceptionHandler(Exception.class) ResponseEntity<ApiError> general(Exception e,HttpServletRequest r){return error(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected server error",r);}
    public record ApiError(LocalDateTime timestamp,int status,String error,String message,String path){}
}