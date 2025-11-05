package com.gym.trainer_service.mapper;

import com.gym.trainer_service.dtos.TrainerDTO;
import com.gym.trainer_service.dtos.TrainerRequest;
import com.gym.trainer_service.models.Trainer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    //De entidad a dto
    TrainerDTO toDto (Trainer trainer);

    //De dto a entidad
    Trainer toEntity(TrainerRequest request);

    //Actualizacion parcial
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TrainerRequest request, @MappingTarget Trainer entity);
}
