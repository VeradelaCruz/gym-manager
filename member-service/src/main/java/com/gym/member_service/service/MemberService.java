package com.gym.member_service.service;

import com.gym.member_service.dtos.MemberDTO;
import com.gym.member_service.dtos.MemberRequest;
import com.gym.member_service.mapper.MemberMapper;
import com.gym.member_service.models.Member;
import com.gym.member_service.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper mapper;

    /// ----CRUD OPERATIONS---
    public MemberDTO createMember(MemberRequest memberRequest){
        Member member= mapper.toEntity(memberRequest);
        memberRepository.save(member);
        return mapper.toDto(member);
    }
}
