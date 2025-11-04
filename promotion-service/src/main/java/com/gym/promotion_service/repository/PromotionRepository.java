package com.gym.promotion_service.repository;

import com.gym.promotion_service.models.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromotionRepository extends MongoRepository<Promotion, String> {

}
