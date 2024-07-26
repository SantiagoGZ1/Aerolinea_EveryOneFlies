package com.BeTek.Aerolinea_EveryOneFlies.Controllers;

import com.BeTek.Aerolinea_EveryOneFlies.Builder.EmailContentBuilder;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FlightRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.PassangerRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Services.EmailService;
import com.BeTek.Aerolinea_EveryOneFlies.Services.PassangerServices;
import com.BeTek.Aerolinea_EveryOneFlies.Services.ReservationService;
import com.BeTek.Aerolinea_EveryOneFlies.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.mail.MessagingException;


import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("api/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;
    private TicketService ticketService;
    private ReservationService reservationService;
    private FlightRepository flightRepository;
    private PassangerServices passangerServices;

    @Operation(summary = "Send ticket by mail", description = "Generates a PDF ticket and sends it by e-mail to the specified address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "PDF successfully generated", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Ticket sent successfully!\" }"))),

        @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{ \"error\": \"Invalid ticket ID format\" }"))),

        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = "{ \"error\": \"Internal server error\" }")))
    })



    @PostMapping("/sendTicket")

        public String sendTicket(@RequestParam String to, @RequestParam Integer ticketId) {
            try {
                byte[] pdfBytes = ticketService.createTicketPdf(ticketId);
                String subject = "Your Flight Ticket";
                String text = "Attached is your flight ticket.";
                emailService.sendTicketEmail(to, subject, text, pdfBytes);
                return "Ticket sent successfully!";
            } catch (MessagingException e) {
                return "Error sending ticket: " + e.getMessage();
            }
        }

    @PostMapping("/sendReservationNotification")
    public String sendReservation(@RequestParam String to, @RequestParam Integer reservationId, @RequestParam Integer passengerId) {
        try {
            Reservation reservation = reservationService.getReservationById(reservationId);
            Optional<Flight> flightOptional = flightRepository.findById(reservation.getFlight().getId());
            Flight flight = flightOptional.get();
            Passenger passenger = passangerServices.getPassenger(passengerId);
            String subject = "Reservation confirmation";
            String emailText = EmailContentBuilder.buildEmailText(reservation, flight, passenger);

            emailService.sendReservationNotification(to, subject, emailText);

            return "Notification email sent successfully";

        } catch (MessagingException e) {
            return "Error sending email: " + e.getMessage();

        }

    }
}
