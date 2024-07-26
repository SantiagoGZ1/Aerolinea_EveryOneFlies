package com.BeTek.Aerolinea_EveryOneFlies.Services;

import com.BeTek.Aerolinea_EveryOneFlies.Exceptions.GeneralException;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }


    public void createFlight(Flight flight) {
        flight.setSeatsBuy(flight.getAvailableSeats());
        this.flightRepository.save(flight);
    }

    public Flight getFlight(Integer id) {
        Optional<Flight> flightOptional = this.flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            return flightOptional.get();
        }
        throw new GeneralException("No exist flight with id " + id);
    }

    public List<Flight> getAllFlights() {
        return this.flightRepository.findAll();
    }

    public void deleteFlight(Integer id) {
        Optional<Flight> flightOptional = this.flightRepository.findById(id);
        if (flightOptional.isEmpty()) {
            throw new GeneralException("No exist");
        }
        this.flightRepository.delete(flightOptional.get());

    }

    public Flight updateFlight(Integer id, Flight flight) {
        Optional<Flight> flightOptional = this.flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            Flight flightToUpdate = flightOptional.get();

            if (flight.getFlightCost() != null) {
                flightToUpdate.setFlightCost(flight.getFlightCost());
            }

            if (flight.getFlightDuration() != null) {
                flightToUpdate.setFlightDuration(flight.getFlightDuration());
            }

            if (flight.getArrivalDate() != null) {
                flightToUpdate.setArrivalDate(flight.getArrivalDate());
            }

            if (flight.getDepartureDate() != null) {
                flightToUpdate.setDepartureDate(flight.getDepartureDate());
            }

            return flightRepository.save(flightToUpdate);
        } else {
            throw new GeneralException("No exist fligh with id " + id);
        }
    }

    public List<Flight> searchFlights(String originAirport, String destinationAirport, LocalDateTime departureDate) {

        return flightRepository.findAll().stream()
                .filter(flight -> originAirport.equals(flight.getOriginAirport()))
                .filter(flight -> destinationAirport.equals(flight.getDestinationAirport()))
                .filter(flight -> departureDate.equals(flight.getDepartureDate()))
                .collect(Collectors.toList());
    }

}
