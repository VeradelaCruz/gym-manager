package com.gym.promotion_service.controller;

import com.gym.promotion_service.dtos.PromotionDTO;
import com.gym.promotion_service.dtos.PromotionRequest;
import com.gym.promotion_service.dtos.PromotionUpdateRequest;
import com.gym.promotion_service.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @PostMapping("/add")
    public ResponseEntity<PromotionDTO> addPromotion(
            @Valid @RequestBody PromotionRequest dto){
        PromotionDTO member= promotionService.createPayment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PromotionDTO>> getAll(){
        return ResponseEntity.ok(
                promotionService.getAll()
        );
    }

    @GetMapping("/id/{idPromotion}")
    public ResponseEntity<PromotionDTO> getById(@PathVariable String idPromotion){
        return ResponseEntity.ok(promotionService.getById(idPromotion));
    }

    @DeleteMapping("/delete/{idPromotion}")
    public ResponseEntity<Void> deletePayment(@PathVariable String idPayment){
        promotionService.deleteById(idPayment);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{idPromotion}")
    public ResponseEntity<PromotionDTO> updatePayment(
            @PathVariable String idPromotion,
            @Valid @RequestBody PromotionUpdateRequest dto){
        PromotionDTO promotion= promotionService.changePromotion(dto, idPromotion);
        return ResponseEntity.ok(promotion);
    }

    @GetMapping("/active")
    public ResponseEntity<List<PromotionDTO>> getActive(){
        return ResponseEntity.ok(promotionService.getActivePromotions());
    }
}
