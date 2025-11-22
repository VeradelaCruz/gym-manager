package com.gym.payment_service.exeption;

public class MemberNotValidException extends RuntimeException {
    public MemberNotValidException(String message) {
        super(message);
    }
}
