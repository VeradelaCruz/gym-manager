package com.gym.reservation_service.events;

import com.gym.reservation_service.enums.Status;
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
    private Status status;
}
