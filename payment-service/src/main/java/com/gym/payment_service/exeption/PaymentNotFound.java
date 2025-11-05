package com.gym.payment_service.exeption;

public class PaymentNotFound extends RuntimeException {
    public PaymentNotFound(String idPayment) {
        super("Payment with id: " + idPayment + " not found.");
    }
}
