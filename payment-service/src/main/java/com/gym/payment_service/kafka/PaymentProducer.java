package com.gym.payment_service.kafka;


import com.gym.payment_service.events.PaymentCreatedEvent;
import com.gym.payment_service.events.PromotionUsedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentCreated(PaymentCreatedEvent event) {
        kafkaTemplate.send("payment-created-topic", event);
    }

    public void sendPromotionUsed(PromotionUsedEvent event) {
        kafkaTemplate.send("promotion-used-topic", event);
    }
}
