package com.gym.reservation_service.models;

import com.gym.reservation_service.enums.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Document(collection = "reservation")
public class Reservation {

    @Id
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

