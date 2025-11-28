package com.gym.member_service.consumer;

import com.gym.member_service.models.Member;
import com.gym.member_service.repository.MemberRepository;
import com.gym.payment_service.events.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final MemberRepository memberRepository;

    @KafkaListener(topics = "payment-created-topic", groupId = "member-group")
    public void consumePaymentEvent(PaymentCreatedEvent event) {
        System.out.println("Evento de pago recibido: " + event);

        // 1️⃣ Buscar el miembro
        Optional<Member> optionalMember = memberRepository.findById(event.getMemberId());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            // 2️⃣ Actualizar historial de pagos
            member.getPayments().add(
                    new PaymentRecord(
                            event.getPaymentId(),
                            event.getFinalAmount(),
                            event.getPaymentDate(),
                            event.getPromotionId(),
                            event.getDiscountPercentage()
                    )
            );

            // 3️⃣ Guardar cambios en MongoDB
            memberRepository.save(member);

            System.out.println("Historial de pagos actualizado para el miembro: " + member.getId());
        } else {
            System.out.println("Miembro no encontrado: " + event.getMemberId());
        }
    }
}
