package com.BeTek.Aerolinea_EveryOneFlies.Models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Flight {
  @Id
  @Column(name = "flight_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String airline;

  @Column
  private String originAirport;

  @Column
  private String destinationAirport;

  @Column
  private LocalDate departureDate;

  @Column
  private LocalTime departureTime;

  @Column
  private LocalDateTime arrivalDate;

  @Column
  private Double flightDuration;

  @Column
  private Integer availableSeats;

  @Column
  private Double flightCost;

  private Integer seatsBuy;

  @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
  @JsonIgnoreProperties("flight")
  private List<Reservation> reservations;

  private List<Integer> seatAssigned = new ArrayList<>();


  public Flight(Integer id, String airline, String originAirport, String destinationAirport, LocalDate departureDate, LocalDateTime arrivalDate, Double flightDuration, Integer availableSeats, Double flightCost, LocalTime departureTime) {
    this.id = id;
    this.airline = airline;
    this.originAirport = originAirport;
    this.destinationAirport = destinationAirport;
    this.departureDate = departureDate;
    this.arrivalDate = arrivalDate;
    this.flightDuration = flightDuration;
    this.availableSeats = availableSeats; //sillas dispo
    this.flightCost = flightCost;
    this.seatsBuy = availableSeats; //sillas dispo
    this.departureTime= departureTime;
  }

  public Flight() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAirline() {
    return airline;
  }

  public void setAirline(String airline) {
    this.airline = airline;
  }

  public String getOriginAirport() {
    return originAirport;
  }

  public void setOriginAirport(String originAirport) {
    this.originAirport = originAirport;
  }

  public String getDestinationAirport() {
    return destinationAirport;
  }

  public void setDestinationAirport(String destinationAirport) {
    this.destinationAirport = destinationAirport;
  }

  public LocalDate getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(LocalDate departureDate) {
    this.departureDate = departureDate;
  }

  public LocalTime getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(LocalTime departureTime) {
    this.departureTime = departureTime;
  }

  public LocalDateTime getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(LocalDateTime arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public Double getFlightDuration() {
    return flightDuration;
  }

  public void setFlightDuration(Double flightDuration) {
    this.flightDuration = flightDuration;
  }

  public Integer getAvailableSeats() {
    return availableSeats;
  }

  public void setAvailableSeats(Integer availableSeats) {
    this.availableSeats = availableSeats;
  }

  public Double getFlightCost() {
    return flightCost;
  }

  public void setFlightCost(Double flightCost) {
    this.flightCost = flightCost;
  }

  public Integer getSeatsBuy() {
    return seatsBuy;
  }

  public void setSeatsBuy(Integer seatsBuy) {
    this.seatsBuy = seatsBuy;
  }

  public List<Reservation> getReservations() {
    return reservations;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  public List<Integer> getSeatAssigned() {
    return seatAssigned;
  }

  public void setSeatAssigned(List<Integer> seatAssigned) {
    this.seatAssigned = seatAssigned;
  }
}
