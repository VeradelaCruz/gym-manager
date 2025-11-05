package com.gym.payment_service.service;

import com.gym.payment_service.dtos.PaymentDTO;
import com.gym.payment_service.exeption.PaymentNotFound;
import com.gym.payment_service.mapper.PaymentMapper;
import com.gym.payment_service.models.Payment;
import com.gym.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    public PaymentRepository paymentRepository;

    @Autowired
    public PaymentMapper mapper;

    /// ----CRUD OPERATIONS---
    //Create
    public PaymentDTO createPayment(PaymentDTO paymentDTO){
        Payment payment= mapper.toEntity(paymentDTO);
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
    public PaymentDTO changePayment(PaymentDTO dto, String idPayment){
        Payment found= paymentRepository.findById(idPayment)
                .orElseThrow(()-> new PaymentNotFound(idPayment));

        mapper.updateFromDto(dto, found);
        Payment updated= paymentRepository.save(found);
        return  mapper.toDto(updated);

    }
}
