package com.gym.trainer_service.exception;

public class TrainerNotFound extends RuntimeException {
    public TrainerNotFound(String idTrainer) {
        super("Trainer with id: "+ idTrainer + " not found");
    }
}
