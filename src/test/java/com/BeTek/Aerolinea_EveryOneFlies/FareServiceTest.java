package com.BeTek.Aerolinea_EveryOneFlies;

import com.BeTek.Aerolinea_EveryOneFlies.Exceptions.GeneralException;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Fare;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FareRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FlightRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Services.FareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FareServiceTest {

    private FlightRepository flightRepository;

    private FareRepository fareRepository;

    private FareService fareService;

    @BeforeEach
    void setUp() {
        this.fareRepository= mock(FareRepository.class);
        this.fareService = new FareService(fareRepository, flightRepository);
    }

    @Test
    void createFareTest() {

        Flight flight = mock(Flight.class);
        Reservation reservation = mock(Reservation.class);
        FlightRepository flightRepository = mock(FlightRepository.class);
        FareRepository fareRepository = mock(FareRepository.class);
        FareService fareService = new FareService(fareRepository, flightRepository);

        when(reservation.getFlight()).thenReturn(flight);
        when(flight.getId()).thenReturn(1);
        when(flightRepository.findById(1)).thenReturn(Optional.of(flight));
        when(reservation.getCategory()).thenReturn("ECONOMY");
        when(fareRepository.save(any(Fare.class))).thenReturn(new Fare()); // mock the save method to return a new Fare object


        Fare result = fareService.createFare(reservation);

        assertNotNull(result);

        verify(fareRepository, times(2)).save(any(Fare.class));


    }

    @Test
    void noExistFareTest() {

        Flight flight = mock(Flight.class);

        List<Passenger> passengers = List.of(
                mock(Passenger.class));

        // Añadir pasajeros según sea necesario
         Reservation reservation = mock(Reservation.class);
            Fare fare = mock(Fare.class);

        Exception e = assertThrows(GeneralException.class, () -> this.fareService.getFare(fare.getId()));

        assertEquals("Fare not found", e.getMessage());

    }

    @Test
    void getExistFare() {

        Flight flight = new Flight(1, "Airline", "Origin", "Destination",
                LocalDate.of(2024, Month.JULY, 20), LocalDateTime.of(2024, Month.JULY, 21, 12, 0),
                3.0, 10, 500.0, LocalTime.of(4,30,0));

        List<Passenger> passengers = new ArrayList<>();
        // Añadir pasajeros según sea necesario
        Reservation reservation = new Reservation(1, LocalDate.of(2024, Month.JULY, 1), 1, passengers, flight, "PREMIUM", null);

        Fare fare = new Fare(reservation, flight.getFlightCost());
        reservation.setFare(fare);

        when(fareRepository.findById(fare.getId())).thenReturn(Optional.of(fare));

        Fare result = fareService.getFare(fare.getId());

        verify(fareRepository, times(1)).findById(fare.getId());
    }

    @Test
    void getAllFares() {

        Flight flight = new Flight(1, "Airline", "Origin", "Destination",
                LocalDate.of(2024, Month.JULY, 20), LocalDateTime.of(2024, Month.JULY, 21, 12, 0),
                3.0, 10, 500.0, LocalTime.of(4,30,0));

        List<Passenger> passengers = new ArrayList<>();
        // Añadir pasajeros según sea necesario
        Reservation reservation = new Reservation(1, LocalDate.of(2024, Month.JULY, 1), 1, passengers, flight, "PREMIUM", null);

        Fare fare = new Fare(reservation, flight.getFlightCost());
        reservation.setFare(fare);

        List<Fare> fares = new ArrayList<>();

        List<Fare> result = fareService.getAllFares();

        verify(fareRepository, times(1)).findAll();

    }

    @Test
    void cantDeleteFare() {
        Flight flight = new Flight(1, "Airline", "Origin", "Destination",
                LocalDate.of(2024, Month.JULY, 20), LocalDateTime.of(2024, Month.JULY, 21, 12, 0),
                3.0, 10, 500.0, LocalTime.of(4,30,0));

        List<Passenger> passengers = new ArrayList<>();
        // Añadir pasajeros según sea necesario
        Reservation reservation = new Reservation(1, LocalDate.of(2024, Month.JULY, 1), 1, passengers, flight, "PREMIUM", null);

        Fare fare = new Fare(reservation, flight.getFlightCost());
        reservation.setFare(fare);

        Exception e = assertThrows(GeneralException.class, () -> this.fareService.deleteFare(fare.getId()));

        assertEquals("Fare not found", e.getMessage());

    }

    @Test
    void deleteFare() {
        Flight flight = new Flight(1, "Airline", "Origin", "Destination",
                LocalDate.of(2024, Month.JULY, 20), LocalDateTime.of(2024, Month.JULY, 21, 12, 0),
                3.0, 10, 500.0, LocalTime.of(4,30,0));

        List<Passenger> passengers = new ArrayList<>();
        // Añadir pasajeros según sea necesario
        Reservation reservation = new Reservation(1, LocalDate.of(2024, Month.JULY, 1), 1, passengers, flight, "PREMIUM", null);

        Fare fare = new Fare(reservation, flight.getFlightCost());
        reservation.setFare(fare);

        when(fareRepository.findById(fare.getId())).thenReturn(Optional.of(fare));

        fareService.deleteFare(fare.getId());

        verify(fareRepository, times(1)).delete(fare);

    }
}
