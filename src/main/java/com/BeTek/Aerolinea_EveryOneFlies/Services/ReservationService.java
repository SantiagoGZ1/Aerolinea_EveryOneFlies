package com.BeTek.Aerolinea_EveryOneFlies.Services;

import com.BeTek.Aerolinea_EveryOneFlies.Builder.EmailContentBuilder;
import com.BeTek.Aerolinea_EveryOneFlies.Exceptions.GeneralException;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Fare;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.FlightRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.PassangerRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.ReservationRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private FlightRepository flightRepository;
    private PassangerRepository passangerRepository;
    private FareService fareService;
    private PassangerServices passangerServices;
    private EmailService emailService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              FlightRepository flightRepository,
                              PassangerRepository passengerRepository,
                              FareService fareService,
                              PassangerServices passangerServices,
                              EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.flightRepository = flightRepository;
        this.fareService = fareService;
        this.passangerRepository = passengerRepository;
        this.passangerServices = passangerServices;
        this.emailService = emailService;
    }

    @SneakyThrows
    public void createReservation(Reservation reservation) {

        Optional<Flight> flightOpt = flightRepository.findById(reservation.getFlight().getId());

        if (flightOpt.isPresent()) {
            Flight flight = flightOpt.get();
            Integer seat = flight.getSeatsBuy();
            if (seat <= 0) {
                throw new GeneralException("Seats not available");
            } else if (reservation.getSeat() > seat) {
                throw new GeneralException("There are only " + seat + " seats available");
            } else {

                //Crea pasajeros
                List<Passenger> passengers = reservation.getPassengers();

                if (passengers.size() == reservation.getSeat()) {
                    passangerServices.createListPassenger(passengers);

                    seat -= reservation.getSeat();
                    flight.setSeatsBuy(seat);
                    flightRepository.save(flight);

                    reservationRepository.save(reservation);
                    //Logica tarifa

                    Fare fare = fareService.createFare(reservation);
                    reservation.setFare(fare);

                    //retorna la reserva
                    reservationRepository.save(reservation);

                    //Envia el email de la reserva
                    sendReservationNotification(reservation);

                    for (Passenger passenger : passengers) {
                        passenger.setReservation(reservation);
                        passangerRepository.save(passenger);
                    }

                } else {
                    throw new GeneralException("Seats do not match the number of passengers");
                }

            }
        } else {
            throw new GeneralException("Flight not found");
        }

    }

    @SneakyThrows
    public void sendReservationNotification(Reservation reservation)  {

        List<Passenger> passengers = reservation.getPassengers();
        String subject = "Reservation confirmation";
        Optional<Flight> flightOptional = flightRepository.findById(reservation.getFlight().getId());
        Flight flight = flightOptional.get();

        for (Passenger passenger : passengers) {
            String email = passenger.getEmail();
            String emailText = EmailContentBuilder.buildEmailText(reservation, flight, passenger);
            emailService.sendReservationNotification(email, subject, emailText);
        }
    }

    public Reservation getReservationById(Integer id) {
        Optional<Reservation> reservationOptional = this.reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            return reservationOptional.get();
        }
        throw new GeneralException("Reservation not found");
    }

    public List<Reservation> getAllReservations() {
        return this.reservationRepository.findAll();
    }

    Integer seats = 0;

    public void deleteReservation(Integer id) {
        Optional<Reservation> reservationOptional = this.reservationRepository.findById(id);

        if (reservationOptional.isPresent()) {
            seats += reservationOptional.get().getSeat() + reservationOptional.get().getFlight().getSeatsBuy();

            Flight flight = reservationOptional.get().getFlight();
            flight.setSeatsBuy(seats);
            this.reservationRepository.delete(reservationOptional.get());


        } else {
            throw new GeneralException("Reservation not found");
        }
    }

    public Reservation updateReservation(Integer id, Reservation reservation) {
        Optional<Reservation> reservationOptional = this.reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            Reservation reservationToUpdate = reservationOptional.get();

            if (reservation.getReservationDate() != null) {
                reservationToUpdate.setReservationDate(reservation.getReservationDate());
            }

            if (reservation.getSeat() != null) {
                reservationToUpdate.setSeat(reservation.getSeat());
            }

            if (reservation.getCategory() != null) {
                reservationToUpdate.setCategory(reservation.getCategory());
            }

            if (reservation.getFare() != null) {
                reservationToUpdate.setFare(reservation.getFare());
            }

            return this.reservationRepository.save(reservationToUpdate);
        } else {
            throw new GeneralException("Reservation not found with id " + id);
        }

    }

}
