package com.BeTek.Aerolinea_EveryOneFlies.Dto;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;

public class ReservationDTO {

    private Integer id;
    private String stateReservation;
    private Reservation reservation;
    private Passenger passenger;

    public ReservationDTO(Integer id, String stateReservation, Reservation reservation, Passenger passenger) {
        this.id = id;
        this.stateReservation = stateReservation;
        this.reservation = reservation;
        this.passenger = passenger;
    }

    public ReservationDTO() {
    }

    public Integer getId() {
        return id;
    }

    public String getStateReservation() {
        return stateReservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Passenger getPassenger() {
        return passenger;
    }
}
