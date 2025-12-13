package com.gym.member_service.events;

import com.gym.member_service.enums.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMemberEvent {
    private String idMember;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate membershipStartDate;
    private MembershipType membershipType;
}
