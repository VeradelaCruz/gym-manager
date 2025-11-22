package com.gym.reservation_service.feign;

import com.gym.reservation_service.dtos.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment-service")
public interface PaymentClient {
    @GetMapping("/payment/id/{idPayment}")
    ResponseEntity<PaymentDTO> getById(@PathVariable String idPayment);
}
