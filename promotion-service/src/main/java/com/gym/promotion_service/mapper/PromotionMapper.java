package com.gym.promotion_service.mapper;

import com.gym.promotion_service.dtos.PromotionDTO;
import com.gym.promotion_service.dtos.PromotionRequest;
import com.gym.promotion_service.dtos.PromotionUpdateRequest;
import com.gym.promotion_service.models.Promotion;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    // Convierte de Request (entrada del controlador) → Entidad (para guardar en BD)
    Promotion toEntity(PromotionRequest promotionRequest);

    // Convierte de Entidad → DTO (para devolver al cliente)
    PromotionDTO toDto(Promotion promotion);

    // Actualiza una entidad existente con los campos del request (solo los no nulos)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(PromotionUpdateRequest request, @MappingTarget Promotion entity);
}