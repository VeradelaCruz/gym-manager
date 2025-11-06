package com.gym.class_service.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FitnessClassResponse {
    private String idClass;
    private String name;
    private String trainer;

    @Min(value = 1, message = "There must be at least 1 participant")
    private Integer maxParticipants;
    @FutureOrPresent(message = "Schedule must be present or future")
    private LocalDateTime scheduleDateTime;
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Long durationMinutes;
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private Double price;
    private boolean active;
}

