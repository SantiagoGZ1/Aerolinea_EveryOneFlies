package com.BeTek.Aerolinea_EveryOneFlies.Services;

import com.BeTek.Aerolinea_EveryOneFlies.Exceptions.GeneralException;
import com.BeTek.Aerolinea_EveryOneFlies.Models.*;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FlightRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.PassangerRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.TicketRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TicketService {

  Random random = new Random();
  private final TicketRepository ticketRepository;
  private final PassangerRepository passangerRepository;
  private FlightRepository flightRepository;
  private TicketPdfExporter ticketPdfExporter;
  private EmailService emailService;

  @Autowired
  public TicketService(TicketRepository ticketRepository, PassangerRepository passangerRepository, FlightRepository flightRepository, TicketPdfExporter ticketPdfExporter, EmailService emailService) {
    this.ticketRepository = ticketRepository;
    this.passangerRepository = passangerRepository;
    this.flightRepository = flightRepository;
    this.ticketPdfExporter = ticketPdfExporter;
    this.emailService = emailService;
  }

  public TicketService(TicketRepository ticketRepository, PassangerRepository passangerRepository, FlightRepository flightRepository, TicketPdfExporter ticketPdfExporter) {
    this.ticketRepository = ticketRepository;
    this.passangerRepository = passangerRepository;
    this.random = random;
    this.flightRepository = flightRepository;
    this.ticketPdfExporter = ticketPdfExporter;
  }

  public Ticket getTicket(Integer id) {
    Optional<Ticket> ticketOptional = this.ticketRepository.findById(id);
    if (ticketOptional.isPresent()) {
      return ticketOptional.get();
    }
    throw new GeneralException("Ticket is not found");
  }

  public List<Ticket> getAllTickets() {
    return this.ticketRepository.findAll();
  }

  public void deleteTicket(Integer id) {
    Optional<Ticket> ticketOptional = this.ticketRepository.findById(id);
    if (ticketOptional.isEmpty()) {
      throw new GeneralException("Ticket not found");
    }
    this.ticketRepository.delete(ticketOptional.get());
  }

  public Ticket updateTicket(Integer id, Ticket ticket) {
    Optional<Ticket> ticketOptional = this.ticketRepository.findById(id);
    if (ticketOptional.isPresent()) {
      Ticket ticketToUpdate = ticketOptional.get();

      if (ticket.getSeat() != null) {
        ticketToUpdate.setSeat(ticket.getSeat());
      }

      return this.ticketRepository.save(ticketToUpdate);
    } else {
      throw new GeneralException("Ticket not found with id " + id);
    }
  }

  public Ticket createTicket(String dni, Integer reservationId) {
    Optional<Passenger> passengerOptional = passangerRepository.findByDni(dni);

    if (passengerOptional.isPresent()) {
      Passenger passenger = passengerOptional.get();
      Reservation reservation = passenger.getReservation();
      if (reservation.getId().equals(reservationId)) {

        Flight flight = reservation.getFlight();
        LocalDateTime flightDeparture = LocalDateTime.of(flight.getDepartureDate(), flight.getDepartureTime());
        LocalDateTime now = LocalDateTime.now();

        long hoursUntilFlight = Duration.between(now, flightDeparture).toHours();
        if (hoursUntilFlight > 24 || hoursUntilFlight < 1) {
          throw new GeneralException("Ticket can only be created between 24 hours and 1 hour before the flight.");
        }

        Integer limitSeats = flight.getAvailableSeats();
        List<Integer> seatAssigned = flight.getSeatAssigned();

        if (seatAssigned.size() <= limitSeats) {
          Integer seat = random.nextInt(limitSeats) + 1;
          if (seatAssigned.size() == limitSeats) {
            throw new GeneralException("All seats assigned");
          } else {
            while (seatAssigned.contains(seat)) {
              seat = random.nextInt(limitSeats) + 1;
            }
            seatAssigned.add(seat);
            Ticket ticket = new Ticket();
            ticket.setPassenger(passenger);
            ticket.setSeat(seat);
            flightRepository.save(flight);
            Ticket ticketCreate = ticketRepository.save(ticket);
            byte[] pdfBytes = createTicketPdf(ticket.getId());
            sendTicketByEmail(passenger, pdfBytes);
            return ticket;
          }
        }
      } else {
        throw new GeneralException("Reservation not found with id " + reservationId);
      }
    }
    throw new GeneralException("Passenger with DNI " + dni + " not found");
  }

  @SneakyThrows
  public void sendTicketByEmail(Passenger passenger, byte[] pdf){
    String email = passenger.getEmail();
    String subject = "Your Flight Ticket";
    String text = "Attached is your flight ticket.";
    emailService.sendTicketEmail(email, subject, text, pdf);
  }

  public byte[] createTicketPdf(Integer ticketId) {
    ModelMap model = new ModelMap();

    Ticket ticket = getTicket(ticketId);
    model.addAttribute("ticket", ticket);

    return ticketPdfExporter.createPdf("ticket", model);
  }

}