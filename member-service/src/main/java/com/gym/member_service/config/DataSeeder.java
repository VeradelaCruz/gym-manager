package com.gym.member_service.config;

import com.gym.member_service.enums.MembershipType;
import com.gym.member_service.models.Member;
import com.gym.member_service.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private MemberRepository repository;

    public DataSeeder(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            List<Member> members = List.of(
                    new Member("Memb01", "Lucía", "Gómez", "lucia.gomez@example.com", "+39 3456789123",
                            LocalDate.of(2025, 1, 10), MembershipType.PREMIUM, true),
                    new Member("Memb02", "Marco", "Rossi", "marco.rossi@example.com", "+39 3341122456",
                            LocalDate.of(2025, 2, 5), MembershipType.BASIC, true),
                    new Member("Memb03", "Sofía", "Bianchi", "sofia.bianchi@example.com", "+39 3338765432",
                            LocalDate.of(2024, 12, 20), MembershipType.STANDARD, false),
                    new Member("Memb04", "Tomás", "Fernández", "tomas.fernandez@example.com", "+39 3895566778",
                            LocalDate.of(2025, 3, 1), MembershipType.PREMIUM, true)
            );
            repository.saveAll(members);
            System.out.println("✅ Datos iniciales de miembros cargados correctamente");
        }
    }


}
