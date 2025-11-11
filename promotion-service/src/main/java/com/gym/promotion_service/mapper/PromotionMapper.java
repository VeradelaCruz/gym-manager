package com.gym.promotion_service.mapper;

import com.gym.promotion_service.dtos.PromotionDTO;
import com.gym.promotion_service.dtos.PromotionRequest;
import com.gym.promotion_service.dtos.PromotionUpdateRequest;
import com.gym.promotion_service.models.Promotion;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    Promotion toEntity(PromotionRequest promotionDTO);

    PromotionDTO toDto(Promotion promotion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void UpdateFromDto(PromotionUpdateRequest request, Promotion entity);
}
