package com.BeTek.Aerolinea_EveryOneFlies.Controllers;


import com.BeTek.Aerolinea_EveryOneFlies.Models.*;
import com.BeTek.Aerolinea_EveryOneFlies.Services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {

  private TicketService ticketService;

  @Autowired
  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }



  @Operation(summary = "Create a new PDF", description = "Creates a new pdf with the reservation data")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "PDF successfully generated", content = @Content(mediaType = "application/pdf", examples = @ExampleObject(value = "{ \"message\": \"PDF successfully generated\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json",
              examples = @ExampleObject(value = "{ \"error\": \"Invalid ticket ID format\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"error\": \"Server malfunction\" }")))
  })

  @GetMapping(path = "/pdf/{ticketId}", produces = MediaType.APPLICATION_PDF_VALUE)
  public @ResponseBody byte[] getPdf(@PathVariable("ticketId") Integer ticketId) {
    return ticketService.createTicketPdf(ticketId);
  }


  @Operation(summary = "Create a new ticket", description = "Creates a ticket for a passenger specified by their DNI and a reservation specified by their ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ticket successfully generated", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Ticket Created\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json",
              examples = @ExampleObject(value = "{ \"error\": \"Invalid reservation ID or DNI format\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"error\": \"Internal server error\" }")))
  })
  @PostMapping(path = "/check-in/reservation/{reservationId}/passenger/{dni}", produces = MediaType.APPLICATION_PDF_VALUE)
  public byte[] createTicket(@PathVariable (name= "reservationId") Integer reservationId, @PathVariable (name = "dni") String dni) {
     Ticket ticket = ticketService.createTicket(dni, reservationId);
     Integer ticketId = ticket.getId();
      return ticketService.createTicketPdf(ticketId);
  }


  @Operation(summary = "Get ticket by ID", description = "Retrieves the details of the ticket specified by its ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ticket successfully obtained", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Ticket obtained\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json",
              examples = @ExampleObject(value = "{ \"error\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"error\": \"Internal server error\" }")))
  })
  @GetMapping("/{id}")
  public Ticket getTicket(@PathVariable Integer id) {
    return ticketService.getTicket(id);
  }


  @Operation(summary = "Get all tickets", description = "You get all tickets in the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ticket successfully obtained", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"All tickets obtained\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json",
              examples = @ExampleObject(value = "{ \"error\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"error\": \"Internal server error\" }")))
  })
  @GetMapping
  public List<Ticket> getAllTickets() {
    return ticketService.getAllTickets();
  }


  @Operation(summary = "Delete a ticket", description = "Remove a ticket from the system by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Delete a ticket successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Delete a ticket successfully\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json",
              examples = @ExampleObject(value = "{ \"error\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"error\": \"Internal server error\" }")))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTicket(@PathVariable Integer id) {
    ticketService.deleteTicket(id);
    return ResponseEntity.ok("Ticket Deleted");
  }

  @Operation(summary = "Update a ticker", description = "Update a ticket from the system by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Update a ticket successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Update a ticket successfully\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json",
              examples = @ExampleObject(value = "{ \"error\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"error\": \"Internal server error\" }")))
  })
  @PutMapping("/{id}")
  public Ticket updateTicket(@PathVariable("id") Integer id, @RequestBody Ticket ticket) {
    return this.ticketService.updateTicket(id, ticket);
  }

}
