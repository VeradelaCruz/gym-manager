package com.gym.payment_service.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PaymentNotFound.class)
    public ResponseEntity<Map<String,String>> PaymentNotFoundHandler(PaymentNotFound ex){
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

    @ExceptionHandler(MemberNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMemberNotValid(MemberNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Member not valid");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MemberServiceUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleMemberServiceUnavailable(MemberServiceUnavailableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Service unavailable");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
