package com.gym.payment_service.producer;


import com.gym.payment_service.events.PaymentCreatedEvent;
import com.gym.payment_service.events.PromotionUsedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, PaymentCreatedEvent> paymentKafkaTemplate;
    private final KafkaTemplate<String, PromotionUsedEvent> promoKafkaTemplate;

    public void sendPaymentCreated(PaymentCreatedEvent event) {
        paymentKafkaTemplate.send("payment-done", event);
    }

    public void sendPromotionUsed(PromotionUsedEvent event) {
        promoKafkaTemplate.send("promotion-used", event);
    }
}
