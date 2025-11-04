package com.gym.promotion_service.models;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Document(collection = "promotion")
public class Promotion {

    @Id
    private String idPromotion;

    @NotBlank(message = "Promotion name cannot be empty")
    private String name;

    @NotNull(message = "Discount percentage cannot be null")
    @Min(value = 0, message = "Discount cannot be less than 0%")
    @Max(value = 100, message = "Discount cannot exceed 100%")
    private Long discountPercentage;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @NotNull(message = "Field appliesToMembershipType cannot be null")
    private Boolean appliesToMembershipType;

    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null) return true;
        return endDate.isAfter(startDate);
    }

}