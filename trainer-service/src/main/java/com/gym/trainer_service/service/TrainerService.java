package com.gym.trainer_service.service;

import com.gym.trainer_service.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {
    @Autowired
    TrainerRepository trainerRepository;
}
