package com.gym.class_service.exceptions;

public class ClassWithTrainerNotFound extends RuntimeException {
    public ClassWithTrainerNotFound(String idClass, String idTrianer) {
        super("Class with id: " + idClass + " has no trainer with id: "+ idTrianer);
    }
}
