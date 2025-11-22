package com.gym.reservation_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ReservationNotFound.class)
    public ResponseEntity<Map<String,String>> ReservationNotFoundHandler(ReservationNotFound ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Resource not found");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MemberNotFound.class)
    public ResponseEntity<Map<String, String>> MemberNotFound(MemberNotFound ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Resource not found");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(MemberServiceUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleMemberServiceUnavailable(MemberServiceUnavailableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Service unavailable");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(PaymentNotFound.class)
    public ResponseEntity<Map<String,String>> PaymentNotFoundHandler(PaymentNotFound ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Resource not found");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ClassNotFound.class)
    public ResponseEntity<Map<String, Object>> handleClassNotFound(ClassNotFound ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Class Not Found");
        error.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MemberServiceUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleClassServiceUnavailable(ClassServiceUnavailableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Service unavailable");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }


}