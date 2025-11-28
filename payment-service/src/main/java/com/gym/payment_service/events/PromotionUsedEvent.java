package com.gym.payment_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionUsedEvent {
    private String idPromotion;
    private String member;
    private String idPayment;
    private Double discountPercentage;
    private LocalDateTime dateUsed;
}
