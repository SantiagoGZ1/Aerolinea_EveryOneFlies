package com.BeTek.Aerolinea_EveryOneFlies.Builder;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;


public class EmailContentBuilder {

    public static String buildEmailText(Reservation reservation, Flight flight, Passenger passenger) {
        StringBuilder sb = new StringBuilder();

        sb.append("Dear ").append(passenger.getName()).append(",\n\n");
        sb.append("Thank you for your reservation. Here are the details of your reservation:\n\n");
        sb.append("Reservation ID: ").append(reservation.getId()).append("\n");
        sb.append("Departure: ").append(flight.getDepartureDate()).append(" from ").append(flight.getOriginAirport()).append("\n");
        sb.append("Arrival: ").append(flight.getArrivalDate()).append(" at ").append(flight.getDestinationAirport()).append("\n");
        sb.append("Thank you for choosing our airline!");

        return sb.toString();

    }
}
