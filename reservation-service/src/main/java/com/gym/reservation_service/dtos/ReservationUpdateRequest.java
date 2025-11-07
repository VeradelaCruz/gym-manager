package com.gym.reservation_service.dtos;

import com.gym.reservation_service.enums.Status;
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
public class ReservationUpdateRequest {
    private String idReservation;

    @NotBlank(message = "Member cannot be empty")
    private String member;

    @NotBlank(message = "Fitness class cannot be empty")
    private String fitnessClass;

    @NotNull(message = "Reservation date cannot be null")
    @FutureOrPresent(message = "Reservation date must be today or in the future")
    private LocalDate reservationDate;

    @NotNull(message = "Status cannot be null")
    private Status status;
}