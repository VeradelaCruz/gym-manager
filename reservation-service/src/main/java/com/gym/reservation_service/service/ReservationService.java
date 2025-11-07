package com.gym.reservation_service.service;

import com.gym.reservation_service.dtos.ReservationDTO;
import com.gym.reservation_service.dtos.ReservationRequest;
import com.gym.reservation_service.dtos.ReservationUpdateRequest;
import com.gym.reservation_service.exception.ReservationNotFound;
import com.gym.reservation_service.mapper.ReservationMapper;
import com.gym.reservation_service.models.Reservation;
import com.gym.reservation_service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper mapper;

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
    public ReservationDTO changeReservation(ReservationUpdateRequest dto, String idReservation){
        Reservation found= reservationRepository.findById(idReservation)
                .orElseThrow(()-> new ReservationNotFound(idReservation));

        mapper.updateFromDto(dto, found);
        Reservation updated= reservationRepository.save(found);
        return  mapper.toDto(updated);

    }
}
