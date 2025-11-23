package com.gym.member_service.feign;

import com.gym.member_service.dtos.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "payment-client")
public interface PaymentClient {
    @GetMapping("/payment/all")
    ResponseEntity<List<PaymentDTO>> getAll();
}
