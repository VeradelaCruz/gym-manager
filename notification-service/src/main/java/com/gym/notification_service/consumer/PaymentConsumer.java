package com.gym.notification_service.consumer;

import com.gym.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailTemplateProcessor templateProcessor;

    @Autowired
    private NotificationProcessor notificationProcessor;

    @KafkaListener(topics = "payment-done", groupId = "notification-service-group")
    public void handlePayment(PaymentEvent event) {

        String template = templateProcessor.loadTemplate("payment-confirmation.html");

        template = templateProcessor.replace(template, "name", event.getMemberName());
        template = templateProcessor.replace(template, "amount", event.getAmount().toString());
        template = templateProcessor.replace(template, "date", event.getDate());

        try {
            emailService.sendHtmlMail(event.getEmail(), "Pago recibido", template);

            notificationProcessor.saveNotification(
                    event.getUserId(),
                    event.getEmail(),
                    "Pago recibido por " + event.getAmount() + "€ el día " + event.getDate(),
                    "payment-service",
                    true
            );

        } catch (Exception e) {

            notificationProcessor.saveNotification(
                    event.getUserId(),
                    event.getEmail(),
                    "Error al enviar notificación de pago",
                    "payment-service",
                    false
            );
        }
    }
}

