package com.gym.reservation_service.models;

import com.gym.reservation_service.enums.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "reservation")
public class Reservation {

    @Id
    private String idReservation;

    private String member;
    private String fitnessClass;
    private LocalDate reservationDate;
    private Status status;
}

