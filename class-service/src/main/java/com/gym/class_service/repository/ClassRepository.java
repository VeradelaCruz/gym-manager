package com.gym.class_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassRepository extends MongoRepository<Class, String> {
}
