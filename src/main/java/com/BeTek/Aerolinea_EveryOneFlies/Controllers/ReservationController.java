package com.BeTek.Aerolinea_EveryOneFlies.Controllers;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;
import com.BeTek.Aerolinea_EveryOneFlies.Services.FareService;
import com.BeTek.Aerolinea_EveryOneFlies.Services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reservation")
public class ReservationController {

  private ReservationService reservationService;
  private FareService fareService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @Operation(summary = "Create a new reservation", description = "Adds a new reservation to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reservation Created", content = @Content(mediaType = "application/json", examples = @ExampleObject(value =  "{ \"message\": \"Reservation successfully created\""))),

      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json",
              examples = @ExampleObject(value = "{ \"error\": \"Invalid reservation information\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"error\": \"Server malfunction\" }")))
  })
  @PostMapping()
  public ResponseEntity<String> createReservation(@RequestBody Reservation reservation)  {
    reservationService.createReservation(reservation);
        return ResponseEntity.ok("Reservation Created");
  }

  @Operation(summary = "Get a reservation", description = "Get a reservation to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reservation successfully obtained", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"}"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json",
              examples = @ExampleObject(value = "{ \"error\": \"Invalid reservation information\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"error\": \"Server malfunction\" }")))
  })
  @GetMapping("/{id}")
  public Reservation getReservation(@PathVariable Integer id){
    return reservationService.getReservationById(id);
  }



  @Operation(summary = "Get all reservations", description = "You get all reservations in the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get all successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"}"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")))
  })
  @GetMapping()
  public List<Reservation> getAllReservation(){
    return reservationService.getAllReservations();
  }


  @Operation(summary = "Delete a reservation", description = "Remove a reservation from the system by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Delete a reservation successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Passenger Deleted\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteReservation(@PathVariable Integer id){
    reservationService.deleteReservation(id);
    return ResponseEntity.ok("Reservation Deleted");
  }
}
