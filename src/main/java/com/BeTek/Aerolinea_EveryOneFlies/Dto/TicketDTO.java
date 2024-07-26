package com.BeTek.Aerolinea_EveryOneFlies.Dto;

import java.time.LocalDate;

public class TicketDTO {

    private Integer id;
    private String flightInfo;
    private LocalDate ticketDate;
    private Integer seat;

    public TicketDTO(Integer id, String flightInfo, LocalDate ticketDate, Integer seat) {
        this.id = id;
        this.flightInfo = flightInfo;
        this.ticketDate = ticketDate;
        this.seat = seat;
    }

    public TicketDTO() {
    }

    public Integer getId() {
        return id;
    }

    public String getFlightInfo() {
        return flightInfo;
    }

    public LocalDate getTicketDate() {
        return ticketDate;
    }

    public Integer getSeat() {
        return seat;
    }
}
