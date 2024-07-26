package com.BeTek.Aerolinea_EveryOneFlies.Controllers;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Fare;
import com.BeTek.Aerolinea_EveryOneFlies.Services.FareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/fare")
public class FareController {

  private FareService fareService;

  @Autowired
  public FareController(FareService fareService) {
    this.fareService = fareService;
  }


  @Operation(summary = "Get a fare", description = "Get a fare from the system by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "fare successfully obtained", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"}")) ),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Invalid input data\"}"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Internal server error\" }")))
  })
  @GetMapping("/{id}")
  public Fare getFare(@PathVariable Integer id){
    return fareService.getFare(id);
  }

  @Operation(summary = "Get all fares", description = "You get all fares in the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get all successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"}"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")))
  })
  @GetMapping
  public List<Fare> getAllFare(){
    return fareService.getAllFares();
  }


  @Operation(summary = "Delete a fare", description = "Remove a fare from the system by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Delete a fare successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Passenger Deleted\" }"))),

      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Invalid input data\" }"))),

      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")))
  })
  @DeleteMapping("/{id}")
  public void deleteFare(@PathVariable Integer id){
    fareService.deleteFare(id);
  }

}
