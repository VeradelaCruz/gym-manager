package com.gym.reservation_service.producer;

import com.gym.reservation_service.events.ReservationMadeEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationProducer {
    private final KafkaTemplate<String, ReservationMadeEvent> reservationKafkaTemplate;
     public  void sendReservationMadeEvent(ReservationMadeEvent event){
         reservationKafkaTemplate.send("reservation-created-topic", event);
     }
}
