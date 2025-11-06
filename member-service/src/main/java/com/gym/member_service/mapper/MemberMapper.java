package com.gym.member_service.mapper;

import com.gym.member_service.dtos.MemberDTO;
import com.gym.member_service.dtos.MemberRequest;
import com.gym.member_service.dtos.MemberUpdateDTO;
import com.gym.member_service.models.Member;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    // Convierte de entidad a dto
     MemberDTO toDto(Member member);

     //Convierte de dto a entidad
     Member toEntity(MemberRequest memberRequest);

     //Upgrades parciales
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(MemberUpdateDTO dto, @MappingTarget Member entity);

}
