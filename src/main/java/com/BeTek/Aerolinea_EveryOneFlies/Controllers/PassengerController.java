package com.BeTek.Aerolinea_EveryOneFlies.Controllers;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import com.BeTek.Aerolinea_EveryOneFlies.Services.PassangerServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/passenger")
@Tag(name = "Passenger", description = "Operations related to passengers")
public class PassengerController {

  private PassangerServices passangerServices;

  @Autowired
  public PassengerController(PassangerServices passangerServices) {
    this.passangerServices = passangerServices;
  }

  @Operation(summary = "Create a new passenger", description = "Adds a new passenger to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Passenger created", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Passenger successfully created\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"error\": \"Invalid passenger information\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json",
          examples = @ExampleObject(value = "{ \"error\": \"Server malfunction\" }")))
  })
  @PostMapping()
    public ResponseEntity<String> createPassenger(@RequestBody Passenger passenger){
      passangerServices.createPassenger(passenger);
      return ResponseEntity.ok("Passenger Created");
  }

  @Operation(summary = "Get a passenger by id", description = "Get a passenger from the system by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Passenger successfully obtained", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"}")) ),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Invalid input data\"}"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Internal server error\" }")))
  })

  @GetMapping("/{id}")
    public Passenger getPassenger (@PathVariable Integer id){
      return passangerServices.getPassenger(id);
  }

  @Operation(summary = "Get a passenger by dni", description = "Get a passenger from the system by its dni")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Passenger successfully obtained", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"}")) ),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Invalid input data\"}"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Internal server error\" }")))
  })
  @GetMapping("/{dni}")
  public Optional<Passenger> getPassengerByDni (@PathVariable String dni){
    return passangerServices.findPassengerByDni(dni);
  }

  @Operation(summary = "Get all passenger", description = "You get all passengers in the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get all successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"}"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")))
  })

  @GetMapping()
  public List<Passenger> getAllPassengers(){
      return passangerServices.getAllPassengers();
  }


  @Operation(summary = "Delete a passenger", description = "Remove a passenger from the system by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Delete a passenger successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Passenger Deleted\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")))
  })
  @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable Integer id){
      passangerServices.deletePassenger(id);
      return ResponseEntity.ok("Passenger Deleted");
  }

  @Operation(summary = "Update a passenger", description = "Update a passenger from the system by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Update a passenger successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"}"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")))
  })
  @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable("id")Integer id, @RequestBody Passenger passenger){
      return this.passangerServices.updatePassenger(id, passenger);
  }

}

