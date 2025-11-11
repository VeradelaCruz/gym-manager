package com.gym.payment_service.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentWithMember {
    private String idPayment;

    private Double amount;
    private LocalDateTime paymentDate;
    private LocalDate validUntil;
    private MemberDTO memberDTO;
}
