package com.gym.class_service.repository;

import com.gym.class_service.models.FitnessClass;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClassRepository extends MongoRepository<FitnessClass, String> {
}
