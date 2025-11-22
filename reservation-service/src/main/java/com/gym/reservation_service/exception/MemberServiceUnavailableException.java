package com.gym.reservation_service.exception;

public class MemberServiceUnavailableException extends RuntimeException {
    public MemberServiceUnavailableException(String message) {
        super(message);
    }
}
