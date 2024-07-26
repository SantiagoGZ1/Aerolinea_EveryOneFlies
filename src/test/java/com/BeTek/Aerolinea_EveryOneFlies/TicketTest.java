package com.BeTek.Aerolinea_EveryOneFlies;

import com.BeTek.Aerolinea_EveryOneFlies.Exceptions.GeneralException;
import com.BeTek.Aerolinea_EveryOneFlies.Models.*;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FlightRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.PassangerRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.ReservationRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.TicketRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketTest {

    private TicketRepository ticketRepository;
    private TicketService ticketService;

    private TicketPdfExporter ticketPdfExporter;

    private PassangerRepository passangerRepository;
    private FlightRepository flightRepository;
    private ReservationRepository reservationRepository;


    @BeforeEach
    void setUp() {
        this.ticketRepository = mock(TicketRepository.class);
        this.passangerRepository = mock(PassangerRepository.class);
        this.flightRepository = mock(FlightRepository.class);
        this.reservationRepository = mock(ReservationRepository.class);
        this.ticketPdfExporter = mock(TicketPdfExporter.class);
        this.ticketService = new TicketService(ticketRepository,passangerRepository, flightRepository,ticketPdfExporter);
    }



//    @Test
//    void createTicketTest() {
//
//
//
//        Flight flight = new Flight();
//        flight.setId(1);
//        flight.setSeatAssigned(new ArrayList<>(Collections.singletonList(5)));
//        flight.setSeatsBuy(1);
//        flight.setAvailableSeats(3);
//        flight.setArrivalDate(LocalDateTime.of(2024,7,26,3,40));
//        flight.setDepartureDate(LocalDate.of(2024,7,25));
//        flight.setDepartureTime(LocalTime.of(16,40));
//
//        Passenger passenger = new Passenger(1, "4356893", null, "Cifuentes", "samuelcifuentes@live.com", LocalDate.of(2003, 7, 5), 3185672, null, null);
//
//
//        Reservation reservation = new Reservation(1, LocalDate.now(), 1, null, flight, null, null);
//        passenger.setReservationFare(reservation);
//        reservation.setFlight(flight);
//
//        Ticket ticket = new Ticket();
//        ticket.setPassenger(passenger);
//        ticket.setSeat(1);
//        ticket.setId(1);
//
//        when(passangerRepository.findByDni(passenger.getDni())).thenReturn(Optional.of(passenger));
//        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
//        when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));
//        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
//
//        // Act
//        Ticket result = ticketService.createTicket(passenger.getDni(), reservation.getId());
//
//        // Assert
//
//        assertNotNull(result);
//        assertEquals(passenger, result.getPassenger());
//        assertEquals(1, result.getSeat());
//        verify(passangerRepository, times(1)).findByDni(passenger.getDni());
//        verify(flightRepository, times(1)).findById(flight.getId());
//        verify(reservationRepository, times(1)).findById(reservation.getId());
//        verify(ticketRepository, times(1)).save(any(Ticket.class));
//    }

    @Test
    void createTicketTest_PassengerNotFound() {
        // Arrange
        String dni = "4356893";
        when(passangerRepository.findByDni(dni)).thenReturn(Optional.empty());

        // Act y Assert
        assertThrows(GeneralException.class, () -> ticketService.createTicket(dni, 1));


    }

    @Test
    void notFoundTicket() {

        Ticket ticket = mock(Ticket.class);

        Exception e = assertThrows(GeneralException.class, () -> this.ticketService.getTicket(ticket.getId()));

        assertEquals("Ticket is not found", e.getMessage());

    }

    @Test
    void GetTicket() {

        Ticket ticket = mock(Ticket.class);

        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.getTicket(ticket.getId());

        verify(ticketRepository, times(1)).findById(ticket.getId());

    }

    @Test
    void GetAllTickets() {

        List<Ticket> ticketList = new ArrayList<>();

        List<Ticket> result = ticketService.getAllTickets();

        verify(ticketRepository, times(1)).findAll();

    }

    @Test
    void deleteNoExistTicket() {

        Ticket ticket = mock(Ticket.class);

        Exception e = assertThrows(GeneralException.class, () -> this.ticketService.deleteTicket(ticket.getId()));

        assertEquals("Ticket not found", e.getMessage());

    }

    @Test
    void deleteTicketTest() {

        Ticket ticket = mock(Ticket.class);

        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        ticketService.deleteTicket(ticket.getId());
        verify(ticketRepository, times(1)).delete(ticket);

    }

    @Test
    void cantUpdateTicket() {
        Ticket ticket = mock(Ticket.class);

        Exception e = assertThrows(GeneralException.class, () -> this.ticketService.updateTicket(ticket.getId(), ticket));

        assertEquals("Ticket not found with id " + ticket.getId(), e.getMessage());

    }

    @Test
    void UpdateTicketTest() {
        Ticket ticket = mock(Ticket.class);

        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        ticket.setSeat(30);
        ticketService.updateTicket(ticket.getId(), ticket);

        verify(ticketRepository, times(1)).findById(ticket.getId());
        verify(ticketRepository, times(1)).save(ticket);

    }

    @Test
    void createTicketPdf() {

        Ticket ticket = new Ticket();
        ticket.setId(1);  // Asignar un ID no nulo

        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        ticketService.createTicketPdf(ticket.getId());

        verify(ticketRepository, times(1)).findById(ticket.getId());

        ArgumentCaptor<String> templatePathCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ModelMap> modelMapCaptor = ArgumentCaptor.forClass(ModelMap.class);

        verify(ticketPdfExporter, times(1)).createPdf(templatePathCaptor.capture(), modelMapCaptor.capture());

        assertEquals("ticket", templatePathCaptor.getValue());
        assertNotNull(modelMapCaptor.getValue());


    }
}
