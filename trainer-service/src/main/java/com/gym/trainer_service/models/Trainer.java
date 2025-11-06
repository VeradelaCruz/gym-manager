package com.gym.trainer_service.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "trainer")
public class Trainer {
    @Id
    String idTrainer;

    private String name;
    private String lastName;
    private String email;
    private String specialty;

}
