package com.gym.reservation_service.mapper;

import com.gym.reservation_service.dtos.ReservationDTO;
import com.gym.reservation_service.dtos.ReservationRequest;
import com.gym.reservation_service.dtos.ReservationUpdateRequest;
import com.gym.reservation_service.models.Reservation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    Reservation toEntity(ReservationRequest request);
    ReservationDTO toDto (Reservation reservation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ReservationUpdateRequest dto, @MappingTarget Reservation reservation);
}
