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

    private String name;
    private Long discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean appliesToMembershipType;
    public boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null) return true;
        return endDate.isAfter(startDate);
    }

}