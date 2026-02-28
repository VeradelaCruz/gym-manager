package com.gym.payment_service.controller;

import com.gym.payment_service.dtos.*;
import com.gym.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add")
    public ResponseEntity<PaymentDTO> addPayment(
            @Valid @RequestBody PaymentRequest dto){
        PaymentDTO member= paymentService.createPayment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> getAll(){
        return ResponseEntity.ok(
                paymentService.getAll()
        );
    }

    @GetMapping("/id/{idPayment}")
    public ResponseEntity<PaymentDTO> getById(@PathVariable String idPayment){
        return ResponseEntity.ok(paymentService.getById(idPayment));
    }

    @DeleteMapping("/delete/{idPayment}")
    public ResponseEntity<Void> deletePayment(@PathVariable String idPayment){
        paymentService.deleteById(idPayment);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{idPayment}")
    public ResponseEntity<PaymentDTO> updatePayment(
            @PathVariable String idPayment,
            @Valid @RequestBody PaymentUpdateRequest dto){
        PaymentDTO payment= paymentService.changePayment(dto, idPayment);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{idPayment}/with-member")
    public ResponseEntity<PaymentWithMember> getWihMember(@PathVariable String idPayment){
        return ResponseEntity.ok(paymentService.findPaymentWithMember(idPayment));
    }

    @GetMapping("/validMembers")
    public ResponseEntity<List<ValidMember>> getValidMembers(){
        return ResponseEntity.ok(paymentService.findValidMembers());
    }

    @GetMapping("/validMember/{idMember}")
    public ResponseEntity<ValidMember> getValidMember(@PathVariable String idMember){
        return ResponseEntity.ok(paymentService.findValidMember(idMember));
    }

    @GetMapping("/byMemberId/{member}")
    private ResponseEntity<List<PaymentDTO>> getByMemberId(@PathVariable String member){
        return ResponseEntity.ok(paymentService.findByMember(member));
    }



}
