package com.BeTek.Aerolinea_EveryOneFlies.Controllers;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Services.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/flight")
public class FlightController {

  private FlightService flightService;

  @Autowired
  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }


  @Operation(summary = "Create a new flight", description = "Adds a new flight to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Flight created", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Flight successfully created\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json",
              examples = @ExampleObject(value = "{ \"error\": \"Invalid flight information\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"error\": \"Server malfunction\" }")))
  })

  @PostMapping()
  public ResponseEntity<String> createFlight(@RequestBody Flight flight){
    flightService.createFlight(flight);
      return ResponseEntity.ok("Flight Created");
  }

  @Operation(summary = "Get a flight", description = "Get a flight from the system by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Flight successfully obtained", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"}")) ),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Invalid input data\"}"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Internal server error\" }")))
  })
  @GetMapping("/{id}")
  public Flight getFlight(@PathVariable Integer id){
    return flightService.getFlight(id);
  }


  @Operation(summary = "Get all flight", description = "You get all flights in the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get all successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"}"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")))
  })
  @GetMapping()
  public List<Flight> getAllFlights(){
    return flightService.getAllFlights();
  }


  @Operation(summary = "Delete a flight", description = "Delete a flight from the system by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Delete a flight successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Passenger Deleted\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteFlight(@PathVariable Integer id){
    flightService.deleteFlight(id);
    return ResponseEntity.ok("Fligth Deleted");
  }

  @Operation(summary = "Search flight", description = "Search for flights that match the specified origin airport, destination airport and departure date.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Flights successfully found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[{\"id\": 1, \"originAirport\": \"JFK\", \"destinationAirport\": \"LAX\", \"departureDate\": \"2024-07-21T10:00:00\", \"arrivalDate\": \"2024-07-21T14:00:00\"}]"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")))
  })
  @GetMapping("/fligths")
  public List<Flight> searchFlights(@RequestParam ("originAirport") String originAirport,
                                    @RequestParam ("destinationAirport") String destinationAirport,
                                    @RequestParam ("departureDate") LocalDateTime departureDate){
    return flightService.searchFlights(originAirport, destinationAirport, departureDate);
  }
}
