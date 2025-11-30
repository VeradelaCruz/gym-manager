package com.gym.notification_service.service;

import com.gym.notification_service.models.Notification;
import com.gym.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void saveNotification(String userId, String email, String message, String sourceService, boolean sent) {

        Notification notification = Notification.builder()
                .userId(userId)
                .email(email)
                .message(message)
                .type("EMAIL")
                .sourceService(sourceService)
                .sent(sent)
                .sentAt(sent ? LocalDateTime.now() : null)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }
}
