package com.gym.reservation_service.controller;

import com.gym.reservation_service.dtos.ReservationDTO;
import com.gym.reservation_service.dtos.ReservationRequest;
import com.gym.reservation_service.dtos.ReservationUpdateRequest;
import com.gym.reservation_service.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/add")
    public ResponseEntity<ReservationDTO> addReservation(
            @Valid @RequestBody ReservationRequest dto){
        ReservationDTO member= reservationService.createReservation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReservationDTO>> getAll(){
        return ResponseEntity.ok(
                reservationService.getAll()
        );
    }

    @GetMapping("/id/{idReservation}")
    public ResponseEntity<ReservationDTO> getById(@PathVariable String idReservation){
        return ResponseEntity.ok(reservationService.getById(idReservation));
    }

    @DeleteMapping("/delete/{idReservation}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String idReservation){
        reservationService.deleteById(idReservation);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{idReservation}")
    public ResponseEntity<ReservationDTO> updatePayment(
            @PathVariable String idReservation,
            @Valid @RequestBody ReservationUpdateRequest dto){
        ReservationDTO member= reservationService.changeReservation(dto, idReservation);
        return ResponseEntity.ok(member);
    }
}
