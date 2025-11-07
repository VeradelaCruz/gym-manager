package com.gym.payment_service.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentUpdateRequest{
    private String idPayment;

    private String member;

    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    private LocalDateTime paymentDate;

    @FutureOrPresent(message = "Valid until date must be in the present or future")
    private LocalDate validUntil;
}