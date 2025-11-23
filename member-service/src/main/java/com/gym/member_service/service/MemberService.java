package com.gym.member_service.service;

import com.gym.member_service.dtos.*;
import com.gym.member_service.enums.MembershipType;
import com.gym.member_service.exception.MemberNotFound;
import com.gym.member_service.exception.MembershipNotFound;
import com.gym.member_service.feign.PaymentClient;
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

    @Autowired
    private PaymentClient paymentClient;

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
    public void deleteById(String idMember){
        Member existing= memberRepository.findById(idMember)
                .orElseThrow(()-> new MemberNotFound(idMember));
        memberRepository.deleteById(existing.getIdMember());
    }

    //Update
    public MemberDTO changeMember(MemberUpdateDTO memberUpdateDTO, String idMember){
        Member found= memberRepository.findById(idMember)
                .orElseThrow(()-> new MemberNotFound(idMember));

        mapper.updateFromDto(memberUpdateDTO, found);
        Member updated= memberRepository.save(found);
        return  mapper.toDto(updated);

    }

    /// --- OTHER OPERATIONS -----
    //Get by membership
    public List<MemberDTO> findByMembership(MembershipType membershipType){
        List<Member> members= memberRepository.findByMembershipType(membershipType);
        return members.stream()
                .map(mapper::toDto)
                .toList();
    }

    //Get historial of payments for a member
    public MemberWithPayments findWithPayments(String idMember){
        //Get member:
        MemberDTO member= getById(idMember);

        //Get all payments:
        List<PaymentDTO> paymentsForMember= paymentClient.getAll().getBody()
                .stream()
                .filter(payment -> payment.getMember().equals(idMember))
                .toList();

        //Build dto
        return MemberWithPayments.builder()
                .idMember(idMember)
                .name(member.getName())
                .lastName(member.getLastName())
                .email(member.getEmail())
                .active(member.getActive())
                .membershipType(member.getMembershipType())
                .membershipStartDate(member.getMembershipStartDate())
                .payments(paymentsForMember)
                .build();
    }
}
