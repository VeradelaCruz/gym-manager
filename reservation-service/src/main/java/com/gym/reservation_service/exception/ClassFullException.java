package com.gym.reservation_service.exception;

public class ClassFullException extends RuntimeException {
    public ClassFullException(String message) {
        super(message);
    }
}
