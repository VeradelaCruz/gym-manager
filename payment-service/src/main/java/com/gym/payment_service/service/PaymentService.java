package com.gym.payment_service.service;

import com.gym.payment_service.dtos.*;
import com.gym.payment_service.exeption.MemberNotFound;
import com.gym.payment_service.exeption.PaymentNotFound;
import com.gym.payment_service.feign.MemberClient;
import com.gym.payment_service.mapper.PaymentMapper;
import com.gym.payment_service.models.Payment;
import com.gym.payment_service.repository.PaymentRepository;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    /// ----CRUD OPERATIONS---
    //Create
    public PaymentDTO createPayment(PaymentRequest paymentRequest){
        Payment payment= mapper.toEntity(paymentRequest);
        paymentRepository.save(payment);
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

}

