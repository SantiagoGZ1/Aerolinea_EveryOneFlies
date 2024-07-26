package com.BeTek.Aerolinea_EveryOneFlies.Dto;

import java.time.LocalDateTime;

public class FlightDTO {

    private Integer id;
    private String airline;
    private String originAirport;
    private String destinationAirport;
    private LocalDateTime departureData;
    private LocalDateTime arrivalDate;
    private Double flightDuration;
    private Double flightCost;

    public FlightDTO(Integer id, String airline, String originAirport, String destinationAirport, LocalDateTime departureData, LocalDateTime arrivalDate, Double flightDuration, Double flightCost) {
        this.id = id;
        this.airline = airline;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.departureData = departureData;
        this.arrivalDate = arrivalDate;
        this.flightDuration = flightDuration;
        this.flightCost = flightCost;
    }

    public FlightDTO() {
    }

    public Integer getId() {
        return id;
    }

    public String getAirline() {
        return airline;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public LocalDateTime getDepartureData() {
        return departureData;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public Double getFlightDuration() {
        return flightDuration;
    }


    public Double getFlightCost() {
        return flightCost;
    }
}
