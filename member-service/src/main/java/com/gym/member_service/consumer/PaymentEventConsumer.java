package com.gym.member_service.consumer;

import com.gym.member_service.exception.MemberNotFound;
import com.gym.member_service.models.Member;
import com.gym.member_service.models.PaymentRecord;
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
        System.out.println("Payment event received: " + event);

        //Buscar el miembro
        Optional<Member> optionalMember = memberRepository.findById(event.getMemberId());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            //Actualizar historial de pagos
            member.getPayments().add(
                    new PaymentRecord(
                            event.getPaymentId(),
                            event.getFinalAmount(),
                            event.getPaymentDate(),
                            event.getPromotionId(),
                            event.getDiscountPercentage()
                    )
            );

            //Guardar cambios en MongoDB
            memberRepository.save(member);

            System.out.println("Updated payments for member: " + member.getIdMember());
        } else {
            throw new MemberNotFound(event.getMemberId());
        }
    }
}
