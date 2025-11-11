package com.gym.payment_service.service;

import com.gym.payment_service.dtos.*;
import com.gym.payment_service.exeption.PaymentNotFound;
import com.gym.payment_service.feign.MemberClient;
import com.gym.payment_service.mapper.PaymentMapper;
import com.gym.payment_service.models.Payment;
import com.gym.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        MemberDTO member = memberClient.getById(payment.getMember()).getBody();

        //Construir el dto:
        return PaymentWithMember.builder()
                .idPayment(idPayment)
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .validUntil(payment.getValidUntil())
                .memberDTO(member)
                .build();
    }

    
}
