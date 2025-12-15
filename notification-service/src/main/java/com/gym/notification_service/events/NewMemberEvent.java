package com.gym.notification_service.events;

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
    private String membershipType;
}
