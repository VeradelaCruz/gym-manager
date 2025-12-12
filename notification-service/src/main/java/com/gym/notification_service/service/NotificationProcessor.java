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

    private final NotificationRepository notificationRepository;
    private final EmailSenderService emailSenderService;
    private final MemberClient memberClient;
    private final EmailTemplateProcessor templateProcessor;

    public void processPaymentCreated(PaymentCreatedEvent event) {

        // Obtenemos datos del miembro
        var member = memberClient.getMemberById(event.getMemberId());
        String userEmail = member.getEmail();
        String memberName = member.getName();

        // Cargamos la plantilla
        String template = templateProcessor.loadTemplate("payment-received.html");

        //Reemplazamos placeholders
        template = templateProcessor.replace(template, "name", memberName);
        template = templateProcessor.replace(template, "amount", String.valueOf(event.getFinalAmount()));
        template = templateProcessor.replace(template, "date", event.getPaymentDate().toString());

        //Guardamos notificación
        Notification notification = Notification.builder()
                .userId(event.getMemberId())
                .email(userEmail)
                .message(template)
                .type("EMAIL")
                .sourceService("payment-service")
                .sent(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        // Enviamos mail
        try {
            emailSenderService.sendEmail(userEmail, "Pago confirmado", template);
            notification.setSent(true);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            notification.setSent(false);
        }

        notificationRepository.save(notification);
    }
}
