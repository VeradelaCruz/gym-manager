package com.gym.notification_service.consumer;

import com.gym.notification_service.events.NewMemberEvent;
import com.gym.notification_service.events.ReservationMadeEvent;
import com.gym.notification_service.service.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationConsumer {
    private final NotificationProcessor notificationProcessor;

    @KafkaListener(
            topics = "reservation-created-topic",
            groupId = "notification-service-group",
            containerFactory = "memberKafkaListenerContainerFactory"
    )
    public void handleNewReservation(ReservationMadeEvent event) {
        notificationProcessor.processReservation(event);
        // Solo pasamos el evento, el processor se encarga de generar el mensaje
        System.out.println("Notification processed for memberId: " + event.getIdMember());
    }
}
