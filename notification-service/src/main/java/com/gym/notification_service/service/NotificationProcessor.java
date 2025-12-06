package com.gym.notification_service.service;

import com.gym.notification_service.dtos.MemberDTO;
import com.gym.notification_service.feign.MemberClient;
import com.gym.notification_service.models.Notification;
import com.gym.notification_service.repository.NotificationRepository;
import com.gym.notification_service.events.PaymentCreatedEvent;
import com.gym.notification_service.exception.MemberNotFound;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class NotificationProcessor {

    private final NotificationRepository notificationRepository;
    private final EmailSenderService emailSenderService;
    @Autowired
    private MemberClient memberClient; // Inyectamos Feign Client

    public void processPaymentCreated(PaymentCreatedEvent event) {

        // Obtener el email real del usuario desde member-service
        String userEmail = getUserEmail(event.getMemberId());

        String message = String.format(
                "Tu pago (%s) ha sido procesado exitosamente por un total de %.2f€.",
                event.getPaymentId(),
                event.getFinalAmount()
        );

        Notification notification = Notification.builder()
                .userId(event.getMemberId())
                .email(userEmail)
                .message(message)
                .type("EMAIL")
                .sourceService("payment-service")
                .sent(false)
                .sentAt(null)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        // Intentar enviar email
        try {
            emailSenderService.sendEmail(userEmail, "Pago confirmado", message);
            notification.setSent(true);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            notification.setSent(false);
        }

        notificationRepository.save(notification);
    }

    private String getUserEmail(String memberId) {
        try {
            MemberDTO member = memberClient.getMemberById(memberId);
            return member.getEmail();
        } catch (Exception e) {
            return String.valueOf(new MemberNotFound(memberId));
        }
    }
}
