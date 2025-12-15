package com.gym.notification_service.service;

import com.gym.notification_service.events.NewMemberEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ThymeleafEmailService {

    private final TemplateEngine templateEngine;

    public String generatePaymentEmail(String name, Double amount, LocalDateTime date) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("amount", amount);
        context.setVariable("date", date);

        return templateEngine.process("payment-confirmation.html", context);

    }

    public String generateNewMemberEmail(String name, String lastName, LocalDate membershipStartDate,
                                         String membershipType, String email, String phone) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("lastName", lastName);
        context.setVariable("membershipStartDate", membershipStartDate);
        context.setVariable("membershipType", membershipType);
        context.setVariable("email", email);
        context.setVariable("phone", phone);
        return templateEngine.process("welcome-member.html", context);
    }
}
