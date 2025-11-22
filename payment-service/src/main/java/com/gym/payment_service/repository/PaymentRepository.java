package com.gym.payment_service.repository;

import com.gym.payment_service.models.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findByMember(String member);

}
