package com.gym.notification_service.feign;

import com.gym.notification_service.dtos.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberClient {

    @GetMapping("/member/id/{idMember}")
    MemberDTO getMemberById(@PathVariable("idMember") String idMember);
}
