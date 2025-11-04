package com.gym.member_service.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Document(collection = "member")
public class Member {

    @Id
    private String idMember;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Indexed(unique = true)
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "\\d{8}", message = "Phone number must have exactly 8 digits")
    private String phone;

    private LocalDate membershipStartDate;

    @NotBlank(message = "Membership type cannot be empty")
    private String membershipType;

    @NotNull(message = "Active status cannot be null")
    private Boolean active;
}
