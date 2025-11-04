package com.gym.trainer_service.controller;

import com.gym.trainer_service.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    @Autowired
    TrainerService trainerService;
}
