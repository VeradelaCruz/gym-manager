package com.gym.reservation_service.repository;

import com.gym.reservation_service.models.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
    long countByFitnessClass(String classId);

}
