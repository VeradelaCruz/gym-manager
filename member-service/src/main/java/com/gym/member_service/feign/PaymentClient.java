package com.gym.member_service.feign;

import com.gym.member_service.dtos.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "payment-client")
public interface PaymentClient {
    @GetMapping("/byMemberId/{member}")
    ResponseEntity<List<PaymentDTO>> getByMemberId(@PathVariable String member);
}
