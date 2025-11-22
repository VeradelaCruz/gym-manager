package com.gym.payment_service.exeption;

public class MemberNotFound extends RuntimeException {
    public MemberNotFound(String message) {
        super( message);
    }
}
