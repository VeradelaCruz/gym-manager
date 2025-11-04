package com.gym.class_service.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "fitness_classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FitnessClass {
    @Id
    private String idClass;

    private String name;
    private String trainer;
    private Integer maxParticipants;
    private LocalDateTime scheduleDateTime;
    private Long durationMinutes;
    private Double price;

    private boolean active;
}

