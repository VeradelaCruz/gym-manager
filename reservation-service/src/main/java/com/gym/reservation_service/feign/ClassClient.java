package com.gym.reservation_service.feign;

import com.gym.reservation_service.dtos.FitnessClassResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "class-service")
public interface ClassClient {
    @GetMapping("/class/id/{idClass}")
     ResponseEntity<FitnessClassResponse> getClassById(@PathVariable String idClass);
}
