package com.gym.reservation_service.config;

import com.gym.reservation_service.enums.Status;
import com.gym.reservation_service.models.Reservation;
import com.gym.reservation_service.repository.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private  ReservationRepository repository;

    public DataSeeder(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            List<Reservation> reservations = List.of(
                    new Reservation("Resv01", "Memb01", "Class01", LocalDate.of(2025, 10, 5), Status.CONFIRMED),
                    new Reservation("Resv02", "Memb02", "Class02", LocalDate.of(2025, 10, 6), Status.CANCELLED),
                    new Reservation("Resv03", "Memb03", "Class03", LocalDate.of(2025, 10, 10), Status.ENDING),
                    new Reservation("Resv04", "Memb04", "Class01", LocalDate.of(2025, 10, 12), Status.CONFIRMED)
            );
            repository.saveAll(reservations);
            System.out.println("✅ Datos iniciales de reservas cargados correctamente");
        }
    }
}
