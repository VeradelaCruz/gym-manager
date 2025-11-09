package com.gym.class_service.feign;

import com.gym.class_service.dtos.TrainerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trainer-service", url = "http://localhost:8084")
public interface TrainerClient {
    @GetMapping("/trainer/id/{idTrainer}")
    ResponseEntity<TrainerDTO> getById(@PathVariable String idTrainer);
}
