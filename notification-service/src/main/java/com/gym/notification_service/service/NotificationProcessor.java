package com.gym.notification_service.service;

import com.gym.notification_service.dtos.MemberDTO;
import com.gym.notification_service.feign.MemberClient;
import com.gym.notification_service.models.Notification;
import com.gym.notification_service.repository.NotificationRepository;
import com.gym.notification_service.events.PaymentCreatedEvent;
import com.gym.notification_service.exception.MemberNotFound;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//Propósito:
//Recibe un evento (por ejemplo PaymentCreatedEvent)
//Coordina todo el flujo:
//Crear Notification
//Generar mensaje (texto o HTML)
//Enviar email
//Guardar/actualizar en Mongo
//Mantiene tu @KafkaListener limpio y enfocado solo en recibir eventos.
@Service
@RequiredArgsConstructor
public class NotificationProcessor {

    private NotificationRepository notificationRepository;
    private EmailSenderService emailSenderService;
    private MemberClient memberClient; // Feign client

    public void processPaymentCreated(PaymentCreatedEvent event, String message) {

        String userEmail;
        try {
            userEmail = memberClient.getMemberById(event.getMemberId()).getEmail();
        } catch (Exception e) {
            userEmail = "no-email@example.com"; // fallback
        }

        Notification notification = Notification.builder()
                .userId(event.getMemberId())
                .email(userEmail)
                .message(message)
                .type("EMAIL")
                .sourceService("payment-service")
                .sent(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        try {
            emailSenderService.sendEmail(userEmail, "Pago confirmado", message);
            notification.setSent(true);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            notification.setSent(false);
        }

        notificationRepository.save(notification);
    }
}

