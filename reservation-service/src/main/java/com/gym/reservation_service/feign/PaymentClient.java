package com.gym.reservation_service.feign;

import com.gym.reservation_service.dtos.ValidMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "payment-service")
public interface PaymentClient {
    @GetMapping("/payment/id/{idPayment}")
    ResponseEntity<ValidMember> getById(@PathVariable String idPayment);

    @GetMapping("/payment/validMember")
    ResponseEntity<List<ValidMember>> getValidMember();
