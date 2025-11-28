package com.gym.payment_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreatedEvent {

    private String paymentId;
    private String memberId;
    private Double finalAmount;
    private LocalDateTime paymentDate;
    private String promotionId;
    private Double discountPercentage; 
}
