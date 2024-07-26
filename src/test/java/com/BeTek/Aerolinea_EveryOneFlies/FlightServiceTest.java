package com.BeTek.Aerolinea_EveryOneFlies;

import com.BeTek.Aerolinea_EveryOneFlies.Exceptions.GeneralException;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FlightRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Services.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FlightServiceTest {

    private FlightRepository flightRepository;
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        this.flightRepository = mock(FlightRepository.class);
        this.flightService = new FlightService(flightRepository);
    }

    @Test
    void createFlightTest() {
        Flight flight = new Flight(2, "Avianca", "El dorado", "Jose Maria Cordoba", LocalDate.of(2020, 12, 12), LocalDateTime.of(2020, 12, 12,4,30,00), 60.5, 50, 20.5, LocalTime.of(5,30,0));

        flightService.createFlight(flight);

        verify(flightRepository, times(1)).save(flight);
    }

    @Test
    void getFlightNoExist() {
        Flight flight = new Flight(2, "Avianca", "El dorado", "Jose Maria Cordoba", LocalDate.of(2020, 12, 12), LocalDateTime.of(2020, 12, 12,4,30,00), 60.5, 50, 20.5, LocalTime.of(5,30,0));

        Exception e = assertThrows(GeneralException.class, () -> this.flightService.getFlight(flight.getId()));

        assertEquals("No exist flight with id " + flight.getId(), e.getMessage());
    }

    @Test
    void getFlightTest(){
        Flight flight = new Flight(2, "Avianca", "El dorado", "Jose Maria Cordoba", LocalDate.of(2020, 12, 12), LocalDateTime.of(2020, 12, 12,4,30,00), 60.5, 50, 20.5, LocalTime.of(5,30,0));

        when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));

        Flight result = flightService.getFlight(flight.getId());

        verify(flightRepository, times(1)).findById(flight.getId());
    }

    @Test
    void getAllFlights() {

        List<Flight> flights = new ArrayList<>();

        List<Flight> result = flightService.getAllFlights();

        verify(flightRepository, times(1)).findAll();

    }

    @Test
    void deleteNoExistFlight() {
        Flight flight = new Flight(2, "Avianca", "El dorado", "Jose Maria Cordoba", LocalDate.of(2020, 12, 12), LocalDateTime.of(2020, 12, 12,4,30,00), 60.5, 50, 20.5, LocalTime.of(5,30,0));

        Exception e = assertThrows(GeneralException.class, () -> this.flightService.deleteFlight(flight.getId()));

        assertEquals("No exist", e.getMessage());

    }

    @Test
    void deleteFlightTest() {
        Flight flight = new Flight(2, "Avianca", "El dorado", "Jose Maria Cordoba", LocalDate.of(2020, 12, 12), LocalDateTime.of(2020, 12, 12,4,30,00), 60.5, 50, 20.5, LocalTime.of(5,30,0));

        when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));

        flightService.deleteFlight(flight.getId());
        verify(flightRepository, times(1)).delete(flight);
    }


    @Test
    void cantUpdateFlight() {
        Flight flight = new Flight(2, "Avianca", "El dorado", "Jose Maria Cordoba", LocalDate.of(2020, 12, 12), LocalDateTime.of(2020, 12, 12,4,30,00), 60.5, 50, 20.5, LocalTime.of(5,30,0));

        Exception e = assertThrows(GeneralException.class, () -> this.flightService.updateFlight(flight.getId(), flight));

        assertEquals("No exist fligh with id " + flight.getId(), e.getMessage());
    }

    @Test
    void updateFlight() {
        Flight flight = new Flight(2, "Avianca", "El dorado", "Jose Maria Cordoba", LocalDate.of(2020, 12, 12), LocalDateTime.of(2020, 12, 12,4,30,00), 60.5, 50, 20.5, LocalTime.of(5,30,0));

        when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));

        flight.setFlightCost(800.4);
        flight.setFlightDuration(60.5);
        flight.setArrivalDate(LocalDateTime.of(2024,12,31,4,30));
        flight.setDepartureDate(LocalDate.of(2024,12,30));

        flightService.updateFlight(flight.getId(), flight);

        verify(flightRepository, times(1)).findById(flight.getId());
        verify(flightRepository, times(1)).save(flight);
    }

//    @Test
//    void searchFlightTest() {
//        List<Flight> flights = Arrays.asList(
//                new Flight(1, "Airline A", "JFK", "LAX", LocalDate.of(2024, 7, 20), LocalDateTime.of(2024, 7, 20, 14, 0), 4.0, 100, 300.0,LocalTime.of(4,30,0)));
//
//        when(flightRepository.findAll()).thenReturn(flights);
//
//        String originAirport = "JFK";
//        String destinationAirport = "LAX";
//        LocalDate departureDateTime = LocalDate.of(2024,7,20);
//
//
//        System.out.println("Departure:" + departureDateTime);
//
//        List<Flight> result = flightService.searchFlights(originAirport, destinationAirport, departureDateTime);
//
//
//        assertThat(result).hasSize(1);
//
//        Flight flight = result.get(0);
//        assertThat(flight.getOriginAirport()).isEqualTo("JFK");
//        assertThat(flight.getDestinationAirport()).isEqualTo("LAX");
//        assertThat(flight.getDepartureDate()).isEqualTo(LocalDate.of(2024, 7, 20));
//        assertThat(flight.getArrivalDate()).isEqualTo(LocalDateTime.of(2024, 7, 20, 14, 0));
//        assertThat(flight.getFlightDuration()).isEqualTo(4.0);
//        assertThat(flight.getAvailableSeats()).isEqualTo(100);
//        assertThat(flight.getFlightCost()).isEqualTo(300.0);
// }
}
