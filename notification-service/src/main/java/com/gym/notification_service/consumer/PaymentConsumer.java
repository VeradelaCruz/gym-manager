package com.gym.notification_service.consumer;

import com.gym.notification_service.service.EmailTemplateProcessor;
import com.gym.notification_service.service.NotificationProcessor;
import com.gym.notification_service.events.PaymentCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class PaymentConsumer {

    @Autowired
    private EmailTemplateProcessor templateProcessor;

    @Autowired
    private NotificationProcessor notificationProcessor;

    @KafkaListener(topics = "payment-created-topic", groupId = "notification-service-group")
    public void handlePayment(PaymentCreatedEvent event) {

        // Preparar mensaje usando la plantilla
        String template = templateProcessor.loadTemplate("payment-confirmation.html");
        template = templateProcessor.replace(template, "id", event.getMemberId());
        template = templateProcessor.replace(template, "amount", event.getFinalAmount().toString());
        template = templateProcessor.replace(template, "date", event.getPaymentDate().toString());

        // Procesar envío
        notificationProcessor.processPaymentCreated(event, template);
    }


}

