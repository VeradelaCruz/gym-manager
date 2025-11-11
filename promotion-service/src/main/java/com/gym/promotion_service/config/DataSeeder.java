package com.gym.promotion_service.config;
import com.gym.promotion_service.models.Promotion;

import com.gym.promotion_service.repository.PromotionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final com.gym.promotion_service.repository.PromotionRepository repository;

    public DataSeeder(PromotionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            List<Promotion> promotions = List.of(
                    new Promotion("Promo01", "October Madness", 20L,
                            LocalDate.of(2025, 10, 1),
                            LocalDate.of(2025, 10, 31),
                            true),

                    new Promotion("Promo02", "Autumn Fit", 15L,
                            LocalDate.of(2025, 9, 15),
                            LocalDate.of(2025, 10, 15),
                            false),

                    new Promotion("Promo03", "New Year Shape Up", 25L,
                            LocalDate.of(2024, 12, 20),
                            LocalDate.of(2025, 1, 31),
                            true),

                    new Promotion("Promo04", "November Gains", 10L,
                            LocalDate.of(2025, 11, 1),
                            LocalDate.of(2025, 11, 30),
                            false)
            );

            repository.saveAll(promotions);
            System.out.println("✅ Datos iniciales de promociones cargados correctamente");
        }
    }
}
