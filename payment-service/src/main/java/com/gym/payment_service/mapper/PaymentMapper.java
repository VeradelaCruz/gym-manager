package com.gym.payment_service.mapper;

import com.gym.payment_service.dtos.PaymentDTO;
import com.gym.payment_service.models.Payment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity (PaymentDTO dto);
    PaymentDTO toDto(Payment payment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PaymentDTO dto, @MappingTarget Payment payment);
}
