package com.gym.promotion_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionUsage {

    private String memberId;
    private String paymentId;
    private LocalDateTime usedDate;
}
