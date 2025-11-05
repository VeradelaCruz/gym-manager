package com.gym.payment_service.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "payment")
public class Payment {
    @Id
    private String idPayment;

    private String member;
    private Double amount;
    private LocalDateTime paymentDate;
    private LocalDate validUntil;
}
