package com.gym.class_service.mapper;

import com.gym.class_service.dtos.FitnessClassCreateRequest;
import com.gym.class_service.dtos.FitnessClassDTO;
import com.gym.class_service.dtos.FitnessClassResponse;
import com.gym.class_service.models.FitnessClass;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FitnessClassMapper {

    //convierte de DTO → entidad
    FitnessClass toEntity(FitnessClassCreateRequest dto);

    //convierte de entidad → DTO.
    FitnessClassResponse toResponse(FitnessClass entity);

    //Le dice a MapStruct: "si un campo en el DTO viene en null, no lo sobrescribas en la entidad".
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    //@MappingTarget
    //Le dice a MapStruct: "en lugar de crear un nuevo objeto, actualiza el que te paso".
    void updateFromDTO(FitnessClassDTO dto, @MappingTarget FitnessClass fitnessClass);
}
