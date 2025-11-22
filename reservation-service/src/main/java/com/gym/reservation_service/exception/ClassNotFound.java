package com.gym.reservation_service.exception;

public class ClassNotFound extends RuntimeException {
    public ClassNotFound(String idClass) {
        super(
                "Class with id: " + idClass + "not found."
        );
    }
}

