package com.gym.promotion_service.consumer;

import com.gym.promotion_service.events.PromotionUsedEvent;
import com.gym.promotion_service.models.PromotionUsage;
import com.gym.promotion_service.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionUsedConsumer {

    private final PromotionRepository promotionRepository;

    @KafkaListener(topics = "promotion-used-topic", groupId = "promotion-group")
    public void consumePromotionUsedEvent(PromotionUsedEvent event) {

        System.out.println("📩 Promotion-service recibió evento: " + event);

        promotionRepository.findById(event.getPromotionId())
                .ifPresent(promo -> {

                    // Agregar nuevo uso al historial
                    promo.getUsedHistory().add(
                            new PromotionUsage(
                                    event.getMemberId(),
                                    event.getPaymentId(),
                                    event.getDateUsed()
                            )
                    );

                    promotionRepository.save(promo);

                    System.out.println("🔥 Promoción " + promo.getIdPromotion() +
                            " actualizada. Total usos: " + promo.getUsedHistory().size());
                });
    }
}
