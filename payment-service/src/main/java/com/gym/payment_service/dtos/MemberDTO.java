package com.gym.payment_service.dtos;

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

    @Indexed(unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "\\d{8}", message = "Phone number must have exactly 8 digits")
    private String phone;

    private LocalDate membershipStartDate;
    private String membershipType;
    private Boolean active;
}