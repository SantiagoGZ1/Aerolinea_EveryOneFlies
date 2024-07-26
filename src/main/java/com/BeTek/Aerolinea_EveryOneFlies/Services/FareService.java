package com.BeTek.Aerolinea_EveryOneFlies.Services;

import com.BeTek.Aerolinea_EveryOneFlies.Exceptions.GeneralException;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Fare;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FareRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class FareService {
    private static final double IVA = 0.19;

    private FareRepository fareRepository;
    private FlightRepository flightRepository;

    @Autowired

    public FareService(FareRepository fareRepository, FlightRepository flightRepository) {
        this.fareRepository = fareRepository;
        this.flightRepository = flightRepository;
    }

    public Fare createFare(Reservation reservation) {
        Optional<Flight> flightCost = flightRepository.findById(reservation.getFlight().getId());
        Fare fare;
        if (flightCost.isPresent()) {
            fare = new Fare(reservation, flightCost.get().getFlightCost());
            fare.setFinalCostxPassenger(calculateCostFare(reservation));
            fareRepository.save(fare);
            fare.setTotalFinalCost(calculateTotalCost(fare));
            return fareRepository.save(fare);
        } else {
            throw new GeneralException("Flight not found");
        }
    }

    public Double calculateCostFare(Reservation reservation) {
        Optional<Flight> flightCost = flightRepository.findById(reservation.getFlight().getId());
        if (flightCost.isPresent()) {
            Flight flight = flightCost.get();
            double baseCost = flight.getFlightCost();
            double finalCostxPassenger = baseCost;


            if (flight.getDepartureDate() != null && (flight.getDepartureDate().getMonth() == Month.JULY ||
                    flight.getDepartureDate().getMonth() == Month.AUGUST ||
                    flight.getDepartureDate().getMonth() == Month.NOVEMBER ||
                    flight.getDepartureDate().getMonth() == Month.DECEMBER)) {
                finalCostxPassenger += baseCost * 0.10;
            }

            if (flight.getAvailableSeats() != null && flight.getAvailableSeats() <= 10) {
                finalCostxPassenger += baseCost * 0.05;
            }

            if (reservation.getCategory().equalsIgnoreCase("PREMIUM")) {
                finalCostxPassenger += baseCost * 0.15;
            }

            if (reservation.getCategory().equalsIgnoreCase("BUSINESS")) {
                finalCostxPassenger += baseCost * 0.25;
            }

            finalCostxPassenger += finalCostxPassenger * IVA;

            return finalCostxPassenger;
        }
        throw new GeneralException("Flight not found");
    }

    public Double calculateTotalCost(Fare fare ) {
        double totalFinalCost;

            totalFinalCost = fare.getFinalCostxPassenger();
            totalFinalCost *= fare.getReservation().getPassengers().size();

            return totalFinalCost;

    }

    public Fare getFare(Integer id) {
        Optional<Fare> fareOptional = fareRepository.findById(id);
        if (fareOptional.isPresent()) {
            return fareOptional.get();
        }

        throw new GeneralException("Fare not found");
    }

    public List<Fare> getAllFares() {
        return fareRepository.findAll();
    }

    public void deleteFare(Integer id) {
        Optional<Fare> fareOptional = fareRepository.findById(id);
        if (fareOptional.isEmpty()) {
            throw new GeneralException("Fare not found");
        }

        fareRepository.delete(fareOptional.get());

    }

}
