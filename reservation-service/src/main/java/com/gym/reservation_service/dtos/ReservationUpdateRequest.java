package com.gym.reservation_service.dtos;

import com.gym.reservation_service.enums.Status;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationUpdateRequest {
    private String idReservation;
    private String member;
    private String fitnessClass;

    @FutureOrPresent(message = "Reservation date must be today or in the future")
    private LocalDate reservationDate;
    private Status status;
}