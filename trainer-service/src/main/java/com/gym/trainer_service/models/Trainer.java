package com.gym.trainer_service.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter @Setter
@Document(collection = "trainer")
public class Trainer {
    @Id
    String idTrainer;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Indexed(unique = true)
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Specialty cannot be empty")
    String specialty;

}
