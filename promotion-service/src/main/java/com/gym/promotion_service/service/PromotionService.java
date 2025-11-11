package com.gym.promotion_service.service;

import com.gym.promotion_service.dtos.PromotionDTO;
import com.gym.promotion_service.dtos.PromotionRequest;
import com.gym.promotion_service.dtos.PromotionUpdateRequest;
import com.gym.promotion_service.exception.PromotionNotFound;
import com.gym.promotion_service.mapper.PromotionMapper;
import com.gym.promotion_service.models.Promotion;
import com.gym.promotion_service.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionMapper mapper;

    ///  ---- CRUD OPERATION ----
    //Create
    public PromotionDTO createPayment(PromotionRequest promotionRequest){
        Promotion promotion= mapper.toEntity(promotionRequest);
        promotionRepository.save(promotion);
        return mapper.toDto(promotion);
    }

    //Read all
    public List<PromotionDTO> getAll(){
        return promotionRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    //Read by id
    public PromotionDTO getById(String idPromotion){
        Promotion found= promotionRepository.findById(idPromotion)
                .orElseThrow(()-> new PromotionNotFound(idPromotion));
        return mapper.toDto(found);
    }

    //Delete
    public void deleteById(String idPromotion){
        Promotion existing= promotionRepository.findById(idPromotion)
                .orElseThrow(()-> new PromotionNotFound(idPromotion));
        promotionRepository.deleteById(existing.getIdPromotion());
    }

    //Update
    public PromotionDTO changePromotion(PromotionUpdateRequest request, String idPayment){
        Promotion found= promotionRepository.findById(idPayment)
                .orElseThrow(()-> new PromotionNotFound(idPayment));

        mapper.UpdateFromDto(request, found);
        Promotion updated= promotionRepository.save(found);
        return  mapper.toDto(updated);
    }

    /// ---OTHER OPERATION ---

    //Get active promotion:
    public List<PromotionDTO> getActivePromotions() {
        LocalDate today = LocalDate.now();
        return promotionRepository.findAll().stream()
                .filter(p -> p.getStartDate().isBefore(today) && p.getEndDate().isAfter(today))
                .map(mapper::toDto)
                .toList();
    }



}
