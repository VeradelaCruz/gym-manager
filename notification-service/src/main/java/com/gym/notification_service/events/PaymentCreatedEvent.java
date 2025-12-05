package com.gym.notification_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//Flujo:
//PaymentService publica el evento (PaymentCreatedEvent) en Kafka.
//MemberService recibe el evento y lo mapea a su propio PaymentCreatedEvent local.
public class PaymentCreatedEvent {

    private String paymentId;
    private String memberId;
    private Double finalAmount;
    private LocalDateTime paymentDate;
    private String promotionId;
    private Long discountPercentage;
}
