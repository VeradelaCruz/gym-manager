package com.gym.trainer_service.dtos;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerUpdateRequest {

    String idTrainer;
    private String name;
    private String lastName;

    @Indexed(unique = true)
    @Email(message = "Email should be valid")
    private String email;
    private String specialty;

}