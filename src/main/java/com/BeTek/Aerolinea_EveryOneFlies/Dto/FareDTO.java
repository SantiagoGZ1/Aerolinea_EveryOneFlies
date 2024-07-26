package com.BeTek.Aerolinea_EveryOneFlies.Dto;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;

public class FareDTO {


    private Flight flight;
    private Reservation reservation;

    public FareDTO(Flight flight, Reservation reservation) {
        this.flight = flight;
        this.reservation = reservation;
    }

    public FareDTO() {
    }


    public Flight getFlight() {
        return flight;
    }

    public Reservation getReservation() {
        return reservation;
    }
}


