package com.gym.member_service.controller;

import com.gym.member_service.dtos.MemberDTO;
import com.gym.member_service.dtos.MemberRequest;
import com.gym.member_service.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/add")
    public ResponseEntity<MemberDTO> addMember(
            @Valid @RequestBody MemberRequest memberRequest){
        MemberDTO member= memberService.createMember(memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberDTO>> getAll(){
        return ResponseEntity.ok(
                memberService.getAll()
        );
    }

    @GetMapping("/id/{idMember}")
    public ResponseEntity<MemberDTO> getById(@PathVariable String idMember){
        return ResponseEntity.ok(memberService.getById(idMember));
    }

    @DeleteMapping("/delete/{idMember}")
    public ResponseEntity<Void> deleteMember(@PathVariable String idMember){
        memberService.deleteById(idMember);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{idMember}")
    public ResponseEntity<MemberDTO> updateMember(
            @PathVariable String idMember,
            @Valid @RequestBody MemberRequest request){
        MemberDTO member= memberService.changeMember(request, idMember);
        return ResponseEntity.ok(member);
    }
}
