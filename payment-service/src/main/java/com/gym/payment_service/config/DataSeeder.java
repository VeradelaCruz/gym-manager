package com.gym.payment_service.config;

import com.gym.payment_service.models.Payment;
import com.gym.payment_service.repository.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private PaymentRepository repository;

    public DataSeeder(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            List<Payment> payments = List.of(
                    new Payment("Pay01", "Memb01", 49.99, LocalDateTime.of(2025, 10, 1, 10, 15), LocalDate.of(2025, 11, 1)),
                    new Payment("Pay02", "Memb02", 29.99, LocalDateTime.of(2025, 9, 20, 9, 30), LocalDate.of(2025, 10, 20)),
                    new Payment("Pay03", "Memb03", 39.99, LocalDateTime.of(2024, 12, 15, 18, 45), LocalDate.of(2025, 1, 15)),
                    new Payment("Pay04", "Memb04", 49.99, LocalDateTime.of(2025, 10, 10, 14, 0), LocalDate.of(2025, 11, 10))
            );
            repository.saveAll(payments);
            System.out.println("✅ Datos iniciales de pagos cargados correctamente");
        }
    }
}
