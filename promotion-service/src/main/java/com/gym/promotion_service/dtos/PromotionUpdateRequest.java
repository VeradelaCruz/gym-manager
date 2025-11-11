package com.gym.promotion_service.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionUpdateRequest {
    private String idPromotion;

    private String name;
    @Min(value = 0, message = "Discount cannot be less than 0%")
    @Max(value = 100, message = "Discount cannot exceed 100%")
    private Long discountPercentage;

    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    private LocalDate endDate;
    
    private Boolean appliesToMembershipType;

    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null) return true;
        return endDate.isAfter(startDate);
    }
}
