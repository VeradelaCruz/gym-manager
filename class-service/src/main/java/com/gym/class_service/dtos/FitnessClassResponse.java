package com.gym.class_service.dtos;

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
    private Integer maxParticipants;
    private LocalDateTime scheduleDateTime;
    private Long durationMinutes;
    private Double price;
    private boolean active;
}

