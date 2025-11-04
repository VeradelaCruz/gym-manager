package com.gym.member_service.service;

import com.gym.member_service.dtos.MemberDTO;
import com.gym.member_service.dtos.MemberRequest;
import com.gym.member_service.exception.MemberNotFound;
import com.gym.member_service.mapper.MemberMapper;
import com.gym.member_service.models.Member;
import com.gym.member_service.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.member;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper mapper;

    /// ----CRUD OPERATIONS---
    //Create
    public MemberDTO createMember(MemberRequest memberRequest){
        Member member= mapper.toEntity(memberRequest);
        memberRepository.save(member);
        return mapper.toDto(member);
    }

    //Read all
    public List<MemberDTO> getAll(){
        return memberRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    //Read by id
    public MemberDTO getById(String idMember){
        Member found= memberRepository.findById(idMember)
                .orElseThrow(()-> new MemberNotFound(idMember));
        return mapper.toDto(found);
    }

    //Delete
    void deleteById(String idMember){
        Member existing= memberRepository.findById(idMember)
                .orElseThrow(()-> new MemberNotFound(idMember));
        memberRepository.deleteById(existing.getIdMember());
    }

    //Update
    public MemberDTO changeMember(MemberRequest memberRequest, String idMember){
        Member found= memberRepository.findById(idMember)
                .orElseThrow(()-> new MemberNotFound(idMember));

        mapper.updateFromDto(memberRequest, found);
        Member updated= memberRepository.save(found);
        return  mapper.toDto(updated);

    }


}
