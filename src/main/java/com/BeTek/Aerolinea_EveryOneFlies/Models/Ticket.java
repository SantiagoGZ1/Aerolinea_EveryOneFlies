package com.BeTek.Aerolinea_EveryOneFlies.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

  @Id
  @Column(name = "ticket_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private Integer seat;

  @OneToOne
  @JoinColumn(name = "passenger_id", referencedColumnName = "passenger_id")
  private Passenger passenger;

  public Ticket(Passenger passenger) {
    this.passenger = passenger;
  }

  public Ticket() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSeat() {
    return seat;
  }

  public void setSeat(Integer seat) {
    this.seat = seat;
  }

  public Passenger getPassenger() {
    return passenger;
  }

  public void setPassenger(Passenger passenger) {
    this.passenger = passenger;
  }
}
