package com.gym.member_service.controller;

import com.gym.member_service.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberRepository {
    @Autowired
    MemberService memberService;
}
