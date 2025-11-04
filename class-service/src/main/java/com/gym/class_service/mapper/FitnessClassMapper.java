package com.gym.class_service.mapper;

import com.gym.class_service.dtos.FitnessClassCreateRequest;
import com.gym.class_service.dtos.FitnessClassResponse;
import com.gym.class_service.dtos.FitnessClassUpdateRequest;
import com.gym.class_service.models.FitnessClass;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FitnessClassMapper {

    // Para crear una nueva entidad desde el DTO de creación
    FitnessClass toEntity(FitnessClassCreateRequest dto);

    // Para devolver al cliente la respuesta
    FitnessClassResponse toResponse(FitnessClass entity);

    // Para actualizar una entidad existente con los datos del DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(FitnessClassUpdateRequest dto, @MappingTarget FitnessClass entity);
}
