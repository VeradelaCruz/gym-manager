package com.gym.class_service.exceptions;

public class TrainerNotFound extends RuntimeException {
    public TrainerNotFound(String idTrainer) {
        super("Trainer with id: "+ idTrainer + " not found");
    }
}

