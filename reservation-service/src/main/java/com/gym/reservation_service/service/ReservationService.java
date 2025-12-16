package com.gym.reservation_service.service;

import com.gym.member_service.enums.MembershipType;
import com.gym.member_service.exception.MembershipNotFound;
import com.gym.reservation_service.dtos.*;
import com.gym.reservation_service.events.ReservationMadeEvent;
import com.gym.reservation_service.exception.*;
import com.gym.reservation_service.feign.ClassClient;
import com.gym.reservation_service.feign.MemberClient;
import com.gym.reservation_service.feign.PaymentClient;
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

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private ClassClient classClient;
    /// ----CRUD OPERATIONS---
    //Create
    public ReservationDTO createReservation(ReservationRequest request){
        //Use other operation for validation
        validateMemberForReservation(request.getMember());
        //Validation for class availability
        validateClassAvailability(request.getFitnessClass());
        Reservation reservation= mapper.toEntity(request);
        reservationRepository.save(reservation);
        //Evento
        ReservationMadeEvent event=new ReservationMadeEvent();
        event.setReservationDate(request.getReservationDate());
        event.setMember(request.getMember());
        event.setFitnessClass(request.getFitnessClass());
        event.setStatus(request.getStatus());

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

    //Get reservations with a type of membership
    public List<ReservationWithMember> findWithMembership(MembershipType membershipType){
        //get members by membership
        List<MemberDTO> members= memberClient.getByMembership(membershipType).getBody();

        if (members == null || members.isEmpty()) {
            throw new MembershipNotFound(membershipType);
        }

        //get idMember for from all members:
        List<String> idMembers= members.stream()
                .map(MemberDTO::getIdMember)
                .toList();
        //Get reservations with idMember:
        List<ReservationDTO> reservations= getAll().stream()
                .filter(r -> idMembers.contains(r.getMember()))
                .toList();

        //build dto
        return reservations.stream()
                .map(reservation-> {
                    MemberDTO memberDTO = members.stream()
                            .filter(mem -> mem.getIdMember().equals(reservation.getMember()))
                            .findFirst()
                            .orElse(null);

        return ReservationWithMember.builder()
                .idReservation(reservation.getIdReservation())
                .memberDTO(memberDTO)
                .reservationDate(reservation.getReservationDate())
                .fitnessClass(reservation.getFitnessClass()) // si lo tienes en tu modelo
                .build();
    })
            .toList();
    }

    //Get a valid member from other microservice:
    public void validateMemberForReservation(String idMember) {
        //Call microservice communication
        ValidMember validMember;
        //Exception handler
        try {
            paymentClient.getValidMember(idMember).getBody();
        } catch (FeignException.NotFound e) {
            throw new MemberNotFound(idMember);
        } catch (FeignException.ServiceUnavailable e) {
            throw new MemberServiceUnavailableException("Member service is unavailable. Please try again later");
        } catch (FeignException e) {
            throw new RuntimeException("Error communicating with the member service: " + e.contentUTF8());
        }
    }

    //Check if a class is available for reservation:
    public void validateClassAvailability(String idClass) {
        try {
            // Call class-microservice
            FitnessClassResponse classResponse = classClient.getClassById(idClass).getBody();

            if (classResponse == null) {
                throw new ClassNotFound(idClass);
            }

            // Count available spots in class-service
            long currentReservations = reservationRepository.countByFitnessClass(idClass);

            // 3. Comparar con el máximo de participantes
            if (currentReservations >= classResponse.getMaxParticipants()) {
                throw new ClassFullException("No spots available for this class");
            }

        } catch (FeignException.NotFound e) {
            throw new ClassNotFound(idClass);
        } catch (FeignException.ServiceUnavailable e) {
            throw new ClassServiceUnavailableException("Class service is unavailable. Please try again later");
        } catch (FeignException e) {
            throw new RuntimeException("Error communicating with the class service: " + e.contentUTF8());
        }
    }


}

