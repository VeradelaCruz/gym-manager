package com.gym.class_service.repository;

import com.gym.class_service.models.FitnessClass;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassRepository extends MongoRepository<FitnessClass, String> {
}
