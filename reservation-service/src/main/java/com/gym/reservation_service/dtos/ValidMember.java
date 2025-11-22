package com.gym.reservation_service.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidMember {

    //Payment data:
    private String idPayment;
    private Double amount;
    private LocalDateTime paymentDate;
    private LocalDate validUntil;

    //Member data:
    private String idMember;
    private String name;
    private String lastName;
    private LocalDate membershipStartDate;
    private String membershipType;
}