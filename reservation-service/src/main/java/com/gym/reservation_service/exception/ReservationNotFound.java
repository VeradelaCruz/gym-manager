package com.gym.reservation_service.exception;

public class ReservationNotFound extends RuntimeException {
    public ReservationNotFound(String idReservation) {
        super(
                "Reservation with id: "+ idReservation+ " not found."
        );
    }
}
