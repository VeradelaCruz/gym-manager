package com.gym.payment_service.exeption;

public class MemberServiceUnavailableException extends RuntimeException {
    public MemberServiceUnavailableException(String message) {
        super(message);
    }
}
