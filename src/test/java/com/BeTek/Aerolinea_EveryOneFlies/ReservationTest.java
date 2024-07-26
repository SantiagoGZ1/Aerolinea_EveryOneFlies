package com.BeTek.Aerolinea_EveryOneFlies;

import com.BeTek.Aerolinea_EveryOneFlies.Exceptions.GeneralException;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FareRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FlightRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.PassangerRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.ReservationRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Services.EmailService;
import com.BeTek.Aerolinea_EveryOneFlies.Services.FareService;
import com.BeTek.Aerolinea_EveryOneFlies.Services.PassangerServices;
import com.BeTek.Aerolinea_EveryOneFlies.Services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationTest {

    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    private FareRepository fareRepository;
    private FareService fareService;

    private FlightRepository flightRepository;

    private PassangerRepository passangerRepository;
    private PassangerServices passangerServices;

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        this.reservationRepository = mock(ReservationRepository.class);
        this.reservationService = new ReservationService(reservationRepository, flightRepository, passangerRepository, fareService, passangerServices, emailService);

    }


    @Test
    void cantGetReservation() {

        this.flightRepository = mock(FlightRepository.class);

        Flight flight = mock(Flight.class);


        Passenger passenger = mock(Passenger.class);


        Reservation reservation = mock(Reservation.class);

        Exception e = assertThrows(GeneralException.class, () ->this.reservationService.getReservationById(reservation.getId()));

        assertEquals("Reservation not found", e.getMessage());

    }

    @Test
    void getReservationTest() {

        this.flightRepository = mock(FlightRepository.class);

        Flight flight = mock(Flight.class);


        Passenger passenger = mock(Passenger.class);


        Reservation reservation = mock(Reservation.class);

        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));

        Reservation reservation1 = reservationService.getReservationById(passenger.getId());

        verify(reservationRepository, times(1)).findById(reservation.getId());

    }

    @Test
    void deleteNoExistReservation() {

        this.flightRepository = mock(FlightRepository.class);

        Flight flight = mock(Flight.class);


        Passenger passenger = mock(Passenger.class);

        Reservation reservation = mock(Reservation.class);

        Exception e = assertThrows(GeneralException.class, () -> this.reservationService.deleteReservation(reservation.getId()));

        assertEquals("Reservation not found", e.getMessage());


    }

//    @Test
//    void deleteReservation(){
//
//        this.flightRepository = mock(FlightRepository.class);
//
//        Flight flight = mock(Flight.class);
//        flight.getId();
//
//        Passenger passenger = mock(Passenger.class);
//
//        Reservation reservation = mock(Reservation.class);
//
//        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
//
//        reservationService.deleteReservation(reservation.getId());
//
//        verify(reservationRepository, times(1)).delete(reservation);
//
//
//
//    }
}
