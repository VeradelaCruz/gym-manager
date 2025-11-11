package com.gym.promotion_service.service;

import com.gym.promotion_service.dtos.PromotionDTO;
import com.gym.promotion_service.dtos.PromotionRequest;
import com.gym.promotion_service.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    ///  ---- CRUD OPERATION ----
    //Create
    public PromotionDTO createPayment(PromotionRequest paymentRequest){
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
}
