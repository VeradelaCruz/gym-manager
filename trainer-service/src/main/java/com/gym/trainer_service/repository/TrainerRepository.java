package com.gym.trainer_service.repository;

import com.gym.trainer_service.models.Trainer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrainerRepository extends MongoRepository<Trainer, String> {
}
