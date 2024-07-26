package com.BeTek.Aerolinea_EveryOneFlies.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table
public class Reservation {

  @Id
  @Column(name = "reservation_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private LocalDate reservationDate;

  @Column
  private Integer seat;

  @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
  @JsonIgnoreProperties("reservation")//
  private List<Passenger> passengers;

  @ManyToOne
  @JoinColumn(name = "flight_id", referencedColumnName = "flight_id")
  @JsonIgnoreProperties("reservations")
  private Flight flight;

  @Column
  private String category;

  @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
  @JsonIgnoreProperties("reservation")
  private Fare fare;


  public Reservation(Integer id, LocalDate reservationDate, Integer seat, List<Passenger> passengers, Flight flight, String category, Fare fare) {
    this.id = id;
    this.reservationDate = reservationDate;
    this.seat = seat;
    this.passengers = passengers;
    this.flight = flight;
    this.category = category;
    this.fare = fare;
  }

  public Reservation() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDate getReservationDate() {
    return reservationDate;
  }

  public void setReservationDate(LocalDate reservationDate) {
    this.reservationDate = reservationDate;
  }

  public Integer getSeat() {
    return seat;
  }

  public void setSeat(Integer seat) {
    this.seat = seat;
  }

  public Flight getFlight() {
    return flight;
  }

  public void setFlight(Flight flight) {
    this.flight = flight;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public List<Passenger> getPassengers() {
    return passengers;
  }

  public void setPassengers(List<Passenger> passengers) {
    this.passengers = passengers;
  }

  public Fare getFare() {
    return fare;
  }

  public void setFare(Fare fare) {
    this.fare = fare;
  }

}
