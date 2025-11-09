package com.gym.class_service.config;

import com.gym.class_service.models.FitnessClass;
import com.gym.class_service.repository.ClassRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private ClassRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            List<FitnessClass> classes = List.of(
                    new FitnessClass("Class01", "Yoga Flow", "Train01", 20,
                            LocalDateTime.of(2025, 11, 6, 9, 30), 60L, 12.5, true),

                    new FitnessClass("Class02", "CrossFit Power", "Train02", 15,
                            LocalDateTime.of(2025, 11, 6, 18, 0), 45L, 15.0, true),

                    new FitnessClass("Class03", "Spinning Intenso", "Train05", 25,
                            LocalDateTime.of(2025, 11, 7, 7, 0), 50L, 10.0, true),

                    new FitnessClass("Class04", "Pilates Core", "Train03", 18,
                            LocalDateTime.of(2025, 11, 7, 11, 0), 55L, 11.0, true),

                    new FitnessClass("Class05", "HIIT Express", "Train06", 12,
                            LocalDateTime.of(2025, 11, 8, 8, 30), 30L, 9.0, true),

                    new FitnessClass("Class06", "Zumba Dance", "Train07", 30,
                            LocalDateTime.of(2025, 11, 8, 19, 0), 60L, 10.5, true)
            );

            repository.saveAll(classes);
            System.out.println("✅ Datos iniciales de clases cargados correctamente.");
        } else {
            System.out.println("ℹ️ Ya existen clases en la base de datos, no se cargaron nuevos datos.");
        }
    }
}