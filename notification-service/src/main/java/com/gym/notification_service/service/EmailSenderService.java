package com.gym.notification_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


//Un servicio para enviar emails (Spring Mail + Mailtrap)

@Service
@AllArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // 👈 HTML = true

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending HTML email", e);
        }
    }
}

