package com.gym.payment_service.service;

import com.gym.payment_service.dtos.*;
import com.gym.payment_service.events.PaymentCreatedEvent;
import com.gym.payment_service.events.PromotionUsedEvent;
import com.gym.payment_service.exeption.MemberNotFound;
import com.gym.payment_service.exeption.MemberNotValidException;
import com.gym.payment_service.exeption.MemberServiceUnavailableException;
import com.gym.payment_service.exeption.PaymentNotFound;
import com.gym.payment_service.feign.MemberClient;
import com.gym.payment_service.feign.PromotionClient;
import com.gym.payment_service.producer.PaymentProducer;
import com.gym.payment_service.mapper.PaymentMapper;
import com.gym.payment_service.models.Payment;
import com.gym.payment_service.repository.PaymentRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper mapper;

    @Autowired
    private MemberClient memberClient;

    @Autowired
    private PromotionClient promotionClient;

    @Autowired
    private PaymentProducer producer;

    /// ----CRUD OPERATIONS---
    //Create payment with a promotion ,  if available
    public PaymentDTO createPayment(PaymentRequest paymentRequest) {

        Payment payment = mapper.toEntity(paymentRequest);
        LocalDate today = LocalDate.now();

        PromotionDTO appliedPromo = null;

        List<PromotionDTO> promotions = promotionClient.getAll().getBody();

        if (promotions != null) {
            appliedPromo = promotions.stream()
                    .filter(promo -> promo.getStartDate() != null && promo.getEndDate() != null)
                    .filter(promo -> !today.isBefore(promo.getStartDate()) &&
                            !today.isAfter(promo.getEndDate()))
                    .findFirst()
                    .orElse(null);

            if (appliedPromo != null) {
                double discountedAmount = payment.getAmount() *
                        (1 - appliedPromo.getDiscountPercentage() / 100.0);
                payment.setAmount(discountedAmount);
            }
        }

        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);

        //EVENTO PRINCIPAL: PaymentCreated
        PaymentCreatedEvent event = new PaymentCreatedEvent(
                payment.getIdPayment(),
                payment.getMember(),
                payment.getAmount(),
                payment.getPaymentDate(),
                appliedPromo != null ? appliedPromo.getIdPromotion() : null,
                appliedPromo != null ? appliedPromo.getDiscountPercentage() : null
        );

        producer.sendPaymentCreated(event);

        //EVENTO ADICIONAL: PromotionUsed
        if (appliedPromo != null) {
            PromotionUsedEvent promoEvent = new PromotionUsedEvent(
                    appliedPromo.getIdPromotion(),
                    payment.getMember(),
                    payment.getIdPayment(),
                    appliedPromo.getDiscountPercentage(),
                    LocalDateTime.now()
            );

            producer.sendPromotionUsed(promoEvent);
        }

        return mapper.toDto(payment);
    }


    //Read all
    public List<PaymentDTO> getAll(){
        return paymentRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    //Read by id
    public PaymentDTO getById(String idPayment){
       Payment found= paymentRepository.findById(idPayment)
                .orElseThrow(()-> new PaymentNotFound(idPayment));
        return mapper.toDto(found);
    }

    //Delete
    public void deleteById(String idPayment){
        Payment existing= paymentRepository.findById(idPayment)
                .orElseThrow(()-> new PaymentNotFound(idPayment));
        paymentRepository.deleteById(existing.getIdPayment());
    }

    //Update
    public PaymentDTO changePayment(PaymentUpdateRequest request, String idPayment){
        Payment found= paymentRepository.findById(idPayment)
                .orElseThrow(()-> new PaymentNotFound(idPayment));

        mapper.updateFromDto(request, found);
        Payment updated= paymentRepository.save(found);
        return  mapper.toDto(updated);

    }

    /// ---- OTHER OPERATIONS----
    //Get payment with member:
    public PaymentWithMember findPaymentWithMember(String  idPayment) {
        //Encontrar el pago:
        PaymentDTO payment = getById(idPayment);

        //Traer el miembro del otro microservicio:
        MemberDTO memberDTO;
        try {
            memberDTO  = memberClient.getById(payment.getMember()).getBody();
        }catch (FeignException.NotFound e) {
            throw new MemberNotFound( payment.getMember());
        } catch (FeignException e) {
            throw new RuntimeException("Error calling member-service: " + e.getMessage());
        }

        //Construir el dto:
        return PaymentWithMember.builder()
                .idPayment(idPayment)
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .validUntil(payment.getValidUntil())
                .memberDTO(memberDTO)
                .build();
    }

    //Get valid members:
    public List<ValidMember> findValidMembers() {
        // Traer todos los pagos
        List<PaymentDTO> payments = paymentRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();

        LocalDate today = LocalDate.now();

        return payments.stream()
                .filter(p -> p.getValidUntil() != null && !p.getValidUntil().isBefore(today)) // pago vigente
                .map(payment -> {
                    MemberDTO memberDTO = null;

                    try {
                        // Llamar al microservicio de miembros
                        ResponseEntity<MemberDTO> response = memberClient.getById(payment.getMember());
                        memberDTO = response.getBody();

                    } catch (FeignException.NotFound e) {
                        // Si el miembro no existe
                        throw new MemberNotFound("Member not found with ID: " + payment.getMember());
                    } catch (FeignException.ServiceUnavailable e) {
                        // Si el servicio está caído o no disponible
                        throw new RuntimeException("Member-service is unavailable. Please try again later.");
                    } catch (FeignException e) {
                        // Otros errores de comunicación HTTP
                        throw new RuntimeException("Error calling member-service: " + e.contentUTF8());
                    }

                    // Validar que el miembro exista y esté activo
                    if (memberDTO != null && Boolean.TRUE.equals(memberDTO.getActive())) {
                        return ValidMember.builder()
                                .idPayment(payment.getIdPayment())
                                .amount(payment.getAmount())
                                .paymentDate(payment.getPaymentDate())
                                .validUntil(payment.getValidUntil())
                                .idMember(memberDTO.getIdMember())
                                .name(memberDTO.getName())
                                .lastName(memberDTO.getLastName())
                                .membershipStartDate(memberDTO.getMembershipStartDate())
                                .membershipType(memberDTO.getMembershipType())
                                .build();
                    }

                    return null; // miembro inactivo o nulo
                })
                .filter(Objects::nonNull)
                .toList();
    }

    //Get a valid member:
    public ValidMember findValidMember(String idMember) {

        // 1. Retrieve payments of the member from MongoDB
        List<PaymentDTO> payments = paymentRepository.findByMember(idMember)
                .stream()
                .map(mapper::toDto)
                .toList();

        if (payments.isEmpty()) {
            throw new MemberNotValidException("The member has no registered payments");
        }

        // 2. Retrieve member information via Feign
        MemberDTO memberDTO;
        try {
            ResponseEntity<MemberDTO> response = memberClient.getById(idMember);
            memberDTO = response.getBody();

            if (memberDTO == null) {
                throw new MemberNotFound("The member does not exist");
            }

        } catch (FeignException.NotFound e) {
            throw new MemberNotFound(idMember);
        } catch (FeignException.ServiceUnavailable e) {
            throw new MemberServiceUnavailableException("Member service is unavailable. Please try again later");
        } catch (FeignException e) {
            throw new RuntimeException("Error communicating with the member service: " + e.contentUTF8());
        }

        // 3. Get the last payment
        PaymentDTO lastPayment = payments.stream()
                .max(Comparator.comparing(PaymentDTO::getPaymentDate))
                .orElseThrow();

        // 4. Check if the payment is still valid
        LocalDate today = LocalDate.now();
        if (lastPayment.getValidUntil() != null &&
                !lastPayment.getValidUntil().isBefore(today)) {

            // 5. Build and return ValidMember DTO
            return ValidMember.builder()
                    .idMember(idMember)
                    .name(memberDTO.getName())
                    .lastName(memberDTO.getLastName())
                    .membershipStartDate(memberDTO.getMembershipStartDate())
                    .membershipType(memberDTO.getMembershipType())
                    .idPayment(lastPayment.getIdPayment())
                    .amount(lastPayment.getAmount())
                    .paymentDate(lastPayment.getPaymentDate())
                    .validUntil(lastPayment.getValidUntil())
                    .build();
        }

        throw new MemberNotValidException("The member's payment is not up to date");
    }


    //Find payments by member id
    public List<PaymentDTO> findByMember(String member){
        List<Payment> payments = paymentRepository.findByMember(member);
        if (payments.isEmpty()) {
            return List.of();
        }        return payments.stream()
                .map(mapper::toDto)
                .toList();
    }


}

