package com.gym.reservation_service.service;

import com.gym.reservation_service.dtos.*;
import com.gym.reservation_service.exception.MemberNotFound;
import com.gym.reservation_service.exception.ReservationNotFound;
import com.gym.reservation_service.feign.MemberClient;
import com.gym.reservation_service.mapper.ReservationMapper;
import com.gym.reservation_service.models.Reservation;
import com.gym.reservation_service.repository.ReservationRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper mapper;

    @Autowired
    private MemberClient memberClient;

    /// ----CRUD OPERATIONS---
    //Create
    public ReservationDTO createReservation(ReservationRequest request){
        Reservation reservation= mapper.toEntity(request);
        reservationRepository.save(reservation);
        return mapper.toDto(reservation);
    }

    //Read all
    public List<ReservationDTO> getAll(){
        return reservationRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    //Read by id
    public ReservationDTO getById(String idReservation){
        Reservation found= reservationRepository.findById(idReservation)
                .orElseThrow(()-> new ReservationNotFound(idReservation));
        return mapper.toDto(found);
    }

    //Delete
    public void deleteById(String idReservation){
        Reservation existing= reservationRepository.findById(idReservation)
                .orElseThrow(()-> new ReservationNotFound(idReservation));
        reservationRepository.deleteById(existing.getIdReservation());
    }

    //Update
    public ReservationDTO changeReservation(ReservationUpdateRequest dto, String idReservation) {
        Reservation found = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new ReservationNotFound(idReservation));

        mapper.updateFromDto(dto, found);
        Reservation updated = reservationRepository.save(found);
        return mapper.toDto(updated);
    }


    //Get a reservation with member
    public ReservationWithMember findWithMember(String idReservation){
        //get reservation
        ReservationDTO reservation= getById(idReservation);
        //get member
        MemberDTO memberDTO;
        try {
            memberDTO = memberClient.getById(reservation.getMember()).getBody();
        } catch (FeignException.NotFound e) {
            throw new MemberNotFound(reservation.getMember());
        }
        return ReservationWithMember.builder()
                .idReservation(idReservation)
                .fitnessClass(reservation.getFitnessClass())
                .reservationDate(reservation.getReservationDate())
                .memberDTO( memberDTO)
                .build();
    }
}
