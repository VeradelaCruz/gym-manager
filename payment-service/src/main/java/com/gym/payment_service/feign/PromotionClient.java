package com.gym.payment_service.feign;

import com.gym.payment_service.dtos.PromotionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "promotion-service")
public interface PromotionClient {
    @GetMapping("/promotion/all")
     ResponseEntity<List<PromotionDTO>> getAll();

    @GetMapping("/promotion/active")
     ResponseEntity<List<PromotionDTO>> getActive();

}
