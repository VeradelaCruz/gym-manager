package com.gym.promotion_service.consumer;

import com.gym.promotion_service.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionUsedConsumer {

    private final PromotionRepository promotionRepository;

}
