package com.gym.payment_service.models;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter @Setter
@Document(collection = "payment")
public class Payment {
    @Id
    private String idPayment;

    @NotBlank(message = "Member ID cannot be blank")
    private String member;  // podrías usar idMember si lo manejás como referencia lógica

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Payment date is required")
    private LocalDateTime paymentDate;

    @NotNull(message = "Valid until date is required")
    @FutureOrPresent(message = "Valid until date must be in the present or future")
    private LocalDate validUntil;
}
