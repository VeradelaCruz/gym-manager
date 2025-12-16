package com.gym.notification_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationMadeEvent {
    private String idReservation;
    private String member;
    private String fitnessClass;
    private LocalDate reservationDate;
    private String status;
}
