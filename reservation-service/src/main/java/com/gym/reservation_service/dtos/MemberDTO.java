package com.gym.reservation_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    private String idMember;

    private String name;
    private String lastName;
    private LocalDate membershipStartDate;
    private String membershipType;

}