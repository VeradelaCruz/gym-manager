package com.gym.notification_service.service;

import com.gym.notification_service.models.Notification;
import com.gym.notification_service.repository.NotificationRepository;
import com.gym.payment_service.events.PaymentCreatedEvent;
import lombok.AllArgsConstructor;
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

    private  NotificationRepository notificationRepository;
    private  EmailSenderService emailSenderService;

    /**
     * Procesa un evento PaymentCreatedEvent y crea una notificación EMAIL
     */
    public void processPaymentCreated(PaymentCreatedEvent event) {

        // Construir el mensaje para el usuario
        String message = String.format(
                "Tu pago (%s) ha sido procesado exitosamente por un total de %.2f€.",
                event.getPaymentId(),
                event.getFinalAmount()
        );

        // Crear la notificación
        Notification notification = Notification.builder()
                .userId(event.getMemberId())
                .email(obtenerEmailDelUsuario(event.getMemberId())) // esto lo vemos abajo
                .message(message)
                .type("EMAIL")                       // Notificación por EMAIL
                .sourceService("payment-service")    // Microservicio origen
                .sent(false)                         // Todavía no enviado
                .sentAt(null)                        // Se completará luego
                .createdAt(LocalDateTime.now())
                .build();

        // Guardar en MongoDB
        notificationRepository.save(notification);

        // Intentar enviar email
        try {
            emailSenderService.sendEmail(
                    notification.getEmail(),
                    "Pago confirmado",
                    notification.getMessage()
            );

            notification.setSent(true);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception e) {
            notification.setSent(false);
        }

        // Guardar actualización del estado
        notificationRepository.save(notification);
    }

    private String obtenerEmailDelUsuario(String memberId) {
        // TEMPORAL:
        // Aquí deberías consultar al microservicio MEMBER para obtener el email del usuario.
        // Por ahora ponemos algo placeholder para que el código compile.
        return "user@example.com";
    }
}
