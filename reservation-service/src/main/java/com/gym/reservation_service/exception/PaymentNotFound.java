package com.gym.reservation_service.exception;

public class PaymentNotFound extends RuntimeException {
    public PaymentNotFound(String idPayment) {
        super("Payment with id: " + idPayment + " not found.");
    }
}
