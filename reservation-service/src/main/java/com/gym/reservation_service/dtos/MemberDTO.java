package com.gym.reservation_service.dtos;

import lombok.*;

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