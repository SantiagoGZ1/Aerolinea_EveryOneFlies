package com.BeTek.Aerolinea_EveryOneFlies.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


@Entity
@Table
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fare_id")
    private Integer id;


    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    @JsonIgnoreProperties("fare")
    private Reservation reservation;

    @Column
    private Double baseCost;


    @Column
    private Double finalCostxPassenger;

    private Double totalFinalCost;

    private Double iva = 0.19;

    public Fare(Reservation reservation, double baseCost) {
        this.reservation = reservation;
        this.baseCost = baseCost;
    }

    public Fare() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(Double baseCost) {
        this.baseCost = baseCost;
    }

    public Double getFinalCostxPassenger() {
        return finalCostxPassenger;
    }

    public void setFinalCostxPassenger(Double finalCostxPassenger) {
        this.finalCostxPassenger = finalCostxPassenger;
    }

    public Double getTotalFinalCost() {
        return totalFinalCost;
    }

    public void setTotalFinalCost(Double totalFinalCost) {
        this.totalFinalCost = totalFinalCost;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }
}
