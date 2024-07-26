package com.BeTek.Aerolinea_EveryOneFlies.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table
public class Passenger {
  @Id
  @Column(name = "passenger_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String dni;

  @Column
  private String name;

  @Column
  private String lastName;

  @Column
  private String email;

  @Column
  private LocalDate birthDate;

  @Column
  private Integer numberCel;

  @ManyToOne
  @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
  @JsonIgnoreProperties("passengers")
  private Reservation reservation;

  @OneToOne(mappedBy = "passenger", cascade = CascadeType.ALL)
  private Ticket ticket;

  public Passenger(Integer id, String dni, String name, String lastName, String email, LocalDate birthDate, Integer numberCel, Reservation reservation, Ticket ticket) {
    this.id = id;
    this.dni = dni;
    this.name = name;
    this.lastName = lastName;
    this.email = email;
    this.birthDate = birthDate;
    this.numberCel = numberCel;
    this.reservation = reservation;
    this.ticket = ticket;
  }

  public Passenger() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public Integer getNumberCel() {
    return numberCel;
  }

  public void setNumberCel(Integer numberCel) {
    this.numberCel = numberCel;
  }

  public Reservation getReservation() {
    return reservation;
  }

  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }
}
