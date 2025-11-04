package com.gym.class_service.exceptions;

public class ClassNotFound extends RuntimeException {
    public ClassNotFound(String idClass) {
        super(
                "Class with id: " + idClass + "not found."
        );
    }
}
