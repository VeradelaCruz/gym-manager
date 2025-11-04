package com.gym.payment_service.controller;

import com.gym.payment_service.service.PaymentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private PaymentService paymentService;
}
