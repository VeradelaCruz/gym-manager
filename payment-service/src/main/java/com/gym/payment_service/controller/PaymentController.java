package com.gym.payment_service.controller;

import com.gym.payment_service.dtos.PaymentDTO;
import com.gym.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private PaymentService paymentService;

    @PostMapping("/add")
    public ResponseEntity<PaymentDTO> addMember(
            @Valid @RequestBody PaymentDTO dto){
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
            @Valid @RequestBody PaymentDTO dto){
        PaymentDTO member= paymentService.changePayment(dto, idPayment);
        return ResponseEntity.ok(member);
    }
}
