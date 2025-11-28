package com.gym.payment_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreatedEvent {
    private String idPayment;
    private String member;
    private Double amount;
    private LocalDateTime paymentDate;
    private String idPromotion;
    private Double discountPercentage;
}