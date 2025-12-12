package com.gym.notification_service.consumer;

import com.gym.notification_service.feign.MemberClient;
import com.gym.notification_service.service.NotificationProcessor;
import com.gym.notification_service.events.PaymentCreatedEvent;
import com.gym.notification_service.service.ThymeleafEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final NotificationProcessor notificationProcessor;

    @KafkaListener(topics = "payment-created-topic", groupId = "notification-service-group")
    public void handlePayment(PaymentCreatedEvent event) {
        // Solo pasamos el evento, el processor se encarga de generar el mensaje
        notificationProcessor.processPaymentCreated(event);

        System.out.println("Notification processed for memberId: " + event.getMemberId());
    }
}


