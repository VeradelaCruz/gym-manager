package com.gym.trainer_service.config;

import com.gym.trainer_service.models.Trainer;
import com.gym.trainer_service.repository.TrainerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private  TrainerRepository repository;

    public DataSeeder(TrainerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            List<Trainer> trainers = List.of(
                    new Trainer("Train01", "Laura", "Martínez", "laura.martinez@gymfit.com", "Yoga"),
                    new Trainer("Train02", "Carlos", "Fernández", "carlos.fernandez@gymfit.com", "CrossFit"),
                    new Trainer("Train03", "Valentina", "Rossi", "valentina.rossi@gymfit.com", "Pilates"),
                    new Trainer("Train04", "Diego", "López", "diego.lopez@gymfit.com", "Boxeo"),
                    new Trainer("Train05", "Sofía", "Ramírez", "sofia.ramirez@gymfit.com", "Spinning"),
                    new Trainer("Train06", "David", "Rojas", "david.rojas@gymfit.com", "HIIT"),
                    new Trainer("Train07", "Mariana", "López", "mariana.lopez@gymfit.com", "Pilates")
            );
            repository.saveAll(trainers);
            System.out.println("✅ Datos iniciales de entrenadores cargados correctamente");
        }
    }
}
