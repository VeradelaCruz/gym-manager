package com.gym.reservation_service.feign;

import com.gym.reservation_service.dtos.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberClient {
    @GetMapping("/member/id/{idMember}")
    ResponseEntity<MemberDTO> getById(@PathVariable String idMember);
}
