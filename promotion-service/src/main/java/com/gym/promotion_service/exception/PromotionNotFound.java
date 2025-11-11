package com.gym.promotion_service.exception;

public class PromotionNotFound extends RuntimeException {
    public PromotionNotFound(String idPromotion) {
        super("Promotion with id: " + idPromotion + " not found.");
    }
}
