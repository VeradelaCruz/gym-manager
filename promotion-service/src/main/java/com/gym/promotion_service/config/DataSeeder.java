package com.gym.promotion_service.config;
import com.gym.promotion_service.models.Promotion;

import com.gym.promotion_service.repository.PromotionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
@Component
public class DataSeeder implements CommandLineRunner {

    private final PromotionRepository repository;

    public DataSeeder(PromotionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {

            List<Promotion> promotions = List.of(
                    Promotion.builder()
                            .idPromotion("Promo01")
                            .name("October Madness")
                            .discountPercentage(20L)
                            .startDate(LocalDate.of(2025, 10, 1))
                            .endDate(LocalDate.of(2025, 10, 31))
                            .appliesToMembershipType(true)
                            .build(),

                    Promotion.builder()
                            .idPromotion("Promo02")
                            .name("Autumn Fit")
                            .discountPercentage(15L)
                            .startDate(LocalDate.of(2025, 9, 15))
                            .endDate(LocalDate.of(2025, 10, 15))
                            .appliesToMembershipType(false)
                            .build(),

                    Promotion.builder()
                            .idPromotion("Promo03")
                            .name("New Year Shape Up")
                            .discountPercentage(25L)
                            .startDate(LocalDate.of(2024, 12, 20))
                            .endDate(LocalDate.of(2025, 1, 31))
                            .appliesToMembershipType(true)
                            .build(),

                    Promotion.builder()
                            .idPromotion("Promo04")
                            .name("November Gains")
                            .discountPercentage(10L)
                            .startDate(LocalDate.of(2025, 11, 1))
                            .endDate(LocalDate.of(2025, 11, 30))
                            .appliesToMembershipType(false)
                            .build()
            );

            repository.saveAll(promotions);
            System.out.println("✅ Datos iniciales de promociones cargados correctamente");
        }
    }
}
