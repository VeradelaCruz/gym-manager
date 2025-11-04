package com.gym.class_service.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FitnessClassCreateRequest {
    @NotBlank(message = "Class name cannot be empty")
    private String name;

    @NotBlank(message = "Trainer name cannot be empty")
    private String trainer;

    @NotNull(message = "Max participants cannot be null")
    @Min(value = 1)
    private Integer maxParticipants;

    @NotNull
    @FutureOrPresent
    private LocalDateTime scheduleDateTime;

    @NotNull
    @Min(1)
    private Long durationMinutes;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private Double price;
}

