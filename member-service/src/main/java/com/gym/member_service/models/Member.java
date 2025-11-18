package com.gym.member_service.models;


import com.gym.member_service.enums.MembershipType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "member")
public class Member {

    @Id
    private String idMember;

    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate membershipStartDate;
    private MembershipType membershipType;
    private Boolean active;

}
