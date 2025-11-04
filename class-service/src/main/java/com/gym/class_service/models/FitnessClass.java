package com.gym.class_service.models;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Getter @Setter
@Document(collection = "FitnessClass")
public class FitnessClass {

    @Id
    private String idClass;

    @NotBlank(message = "FitnessClass name cannot be empty")
    private String name;

    @NotBlank(message = "Trainer name cannot be empty")
    private String trainer;

    @NotNull(message = "Max participants cannot be null")
    @Min(value = 1, message = "There must be at least 1 participant")
    private Integer maxParticipants;

    @NotNull(message = "Schedule date and time cannot be null")
    @FutureOrPresent(message = "Schedule must be in the present or future")
    private LocalDateTime scheduleDateTime;

    @NotNull(message = "Duration cannot be null")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Long durationMinutes;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private Double price;
}
