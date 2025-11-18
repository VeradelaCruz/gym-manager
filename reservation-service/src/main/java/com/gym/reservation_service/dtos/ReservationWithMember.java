package com.gym.reservation_service.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationWithMember {
    private String idReservation;

    @NotBlank(message = "Fitness class cannot be empty")
    private String fitnessClass;

    @NotNull(message = "Reservation date cannot be null")
    @FutureOrPresent(message = "Reservation date must be today or in the future")
    private LocalDate reservationDate;

    private MemberDTO memberDTO;
}
