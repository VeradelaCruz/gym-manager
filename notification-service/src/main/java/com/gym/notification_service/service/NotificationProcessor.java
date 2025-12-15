package com.gym.notification_service.service;

import com.gym.notification_service.events.NewMemberEvent;
import com.gym.notification_service.feign.MemberClient;
import com.gym.notification_service.models.Notification;
import com.gym.notification_service.repository.NotificationRepository;
import com.gym.notification_service.events.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
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

    private final NotificationRepository notificationRepository;
    private final EmailSenderService emailSenderService;
    private final MemberClient memberClient;
    private final ThymeleafEmailService thymeleafEmailService;

    public void processPaymentCreated(PaymentCreatedEvent event) {

        var member = memberClient.getMemberById(event.getMemberId());
        String userEmail = member.getEmail();

        // Generamos el mensaje con Thymeleaf
        String message = thymeleafEmailService.generatePaymentEmail(
                member.getName(),
                event.getFinalAmount(),
                event.getPaymentDate()
        );

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
            emailSenderService.sendHtmlEmail(userEmail, "Pago confirmado", message);
            notification.setSent(true);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            notification.setSent(false);
        }

        notificationRepository.save(notification);
    }

    public void processNewMember(NewMemberEvent event) {

        String userEmail = event.getEmail();

        //HTML con Thymeleaf
        String message = thymeleafEmailService.generateNewMemberEmail(
                event.getName(),
                event.getLastName(),
                event.getMembershipStartDate(),
                event.getMembershipType(),
                event.getEmail(),
                event.getPhone()
        );

        // Guardo la notificación
        Notification notification = Notification.builder()
                .userId(event.getIdMember())
                .email(userEmail)
                .message(message)
                .type("EMAIL")
                .sourceService("member-service")
                .sent(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        // Envia el mail
        try {
            emailSenderService.sendHtmlEmail(
                    userEmail,
                    "¡Bienvenido/a a Gym Manager!",
                    message
            );
            notification.setSent(true);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            notification.setSent(false);
        }

        notificationRepository.save(notification);
    }

}
