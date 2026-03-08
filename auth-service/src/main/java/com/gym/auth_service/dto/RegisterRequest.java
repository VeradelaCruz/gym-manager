package com.gym.auth_service.dto;

import com.gym.auth_service.security.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String memberId;

    private Role roles;

}
