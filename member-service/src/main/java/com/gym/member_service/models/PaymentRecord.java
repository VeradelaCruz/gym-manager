package com.gym.member_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


//Clase embebible donde guardamos el historial de pago de cada miembro:
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRecord {
    private String paymentId;
    private Double amount;
    private LocalDateTime paymentDate;
    private String promotionId;
    private Long discountPercentage;
}
