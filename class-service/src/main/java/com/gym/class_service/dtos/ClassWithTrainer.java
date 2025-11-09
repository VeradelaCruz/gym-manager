package com.gym.class_service.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassWithTrainer {
    private String idClass;

    private String name;
    private Integer maxParticipants;
    private LocalDateTime scheduleDateTime;
    private Long durationMinutes;

    private TrainerDTO trainerDTO;
}
