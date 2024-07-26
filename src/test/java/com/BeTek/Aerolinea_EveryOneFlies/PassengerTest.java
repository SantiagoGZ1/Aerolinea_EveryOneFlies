package com.BeTek.Aerolinea_EveryOneFlies;

import com.BeTek.Aerolinea_EveryOneFlies.Exceptions.GeneralException;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Ticket;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.PassangerRepository;
import com.BeTek.Aerolinea_EveryOneFlies.Services.PassangerServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PassengerTest {

    private PassangerRepository passangerRepository;
    private PassangerServices passangerServices;

    @BeforeEach
    void setUp() {
        this.passangerRepository = mock(PassangerRepository.class);
        this.passangerServices = new PassangerServices(passangerRepository);
    }

    @Test
    void createPassengerTest() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger = new Passenger(1,"4356893","Samuel","Cifuentes","samuelcifuentes@live.com", LocalDate.of(2003, 7, 5),3185672,reservation,ticket);

        passangerServices.createPassenger(passenger);

        verify(passangerRepository, times(1)).save(passenger);
    }

    @Test
    void createPassengerNameNull(){

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger = new Passenger(1,"4356893",null,"Cifuentes","samuelcifuentes@live.com", LocalDate.of(2003, 7, 5),3185672,reservation,ticket);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createPassenger(passenger));
        assertEquals("Name is required", e.getMessage());
    }

    @Test
    void createPassangerDniNull() {
        Passenger passenger = mock(Passenger.class);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createPassenger(passenger));

        assertEquals("Dni is required", e.getMessage());

    }

    @Test
    void createPassengerDniMustBeNumeric() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger = new Passenger(1,"asasasa","Samuel","Cifuentes","samuelcifuentes@live.com", LocalDate.of(2003, 7, 5),3185672,reservation,ticket);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createPassenger(passenger));

        assertEquals("Dni must be numeric", e.getMessage());

    }

    @Test
    void createPassangerLastNull() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger = new Passenger(1,"4356893","Samuel",null,"samuelcifuentes@live.com", LocalDate.of(2003, 7, 5),3185672,reservation,ticket);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createPassenger(passenger));

        assertEquals("Last name is required", e.getMessage());

    }

    @Test
    void createPassangerEmailIsRequired() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger = new Passenger(1,"4356893","Samuel","Cifuentes",null, LocalDate.of(2003, 7, 5),3185672,reservation,ticket);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createPassenger(passenger));

        assertEquals("Email is required", e.getMessage());
    }

    @Test
    void createPassangerEmailFormatWrong() {
        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger = new Passenger(1,"4356893","Samuel","Cifuentes","samuelcifuentes", LocalDate.of(2003, 7, 5),3185672,reservation,ticket);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createPassenger(passenger));

        assertEquals("Invalid email, validate that it is well written", e.getMessage());

    }

    @Test
    void createPassangerBirthDateIsNull() {
        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger = new Passenger(1,"4356893","Samuel","Cifuentes","samuelcifuentes@live.com", null,3185672,reservation,ticket);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createPassenger(passenger));

        assertEquals("birth date is required", e.getMessage());

    }

    @Test
    void createPassangerNumberCellIsNull() {
        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger = new Passenger(1,"4356893","Samuel","Cifuentes","samuelcifuentes@live.com", LocalDate.of(2003, 7, 5),null,reservation,ticket);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createPassenger(passenger));

        assertEquals("phone number is required", e.getMessage());

    }

    @Test
    void noExistPassengerTest() {

        Passenger passenger = mock(Passenger.class);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.getPassenger(passenger.getId()));

        assertEquals("No exist passenger with id " + passenger.getId(), e.getMessage());

    }

    @Test
    void getPassengerExist() {
        Passenger passenger = mock(Passenger.class);

        when(passangerRepository.findById(passenger.getId())).thenReturn(Optional.of(passenger));

        Passenger result = passangerServices.getPassenger(passenger.getId());

        verify(passangerRepository, times(1)).findById(passenger.getId());
    }

    @Test
    void getAllPassengers() {

        List<Passenger> passengers = new ArrayList<>();

        List<Passenger> result = passangerServices.getAllPassengers();

        verify(passangerRepository,times(1)).findAll();
    }

    @Test
    void deleteNoExistPassenger() {
        Passenger passenger = mock(Passenger.class);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.deletePassenger(passenger.getId()));

        assertEquals("Passenger no exist", e.getMessage());

    }

    @Test
    void deletePassenger() {
        Passenger passenger = mock(Passenger.class);

        when(passangerRepository.findById(passenger.getId())).thenReturn(Optional.of(passenger));

        passangerServices.deletePassenger(passenger.getId());
        verify(passangerRepository, times(1)).delete(passenger);
    }


    @Test
    void cantUpdatePassenger() {
        Passenger passenger = mock(Passenger.class);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.updatePassenger(passenger.getId(), passenger));

        assertEquals("The passenger no exist with id " + passenger.getId(), e.getMessage());
    }


    @Test
    void updatePassenger() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger = new Passenger(1,"4356893","Samuel","Cifuentes","samuelcifuentes@live.com", LocalDate.of(2003, 7, 5),3185672,reservation,ticket);



        when(passangerRepository.findById(passenger.getId())).thenReturn(Optional.of(passenger));

        passenger.setName("Mateo");
        passangerServices.updatePassenger(passenger.getId(), passenger);

        verify(passangerRepository, times(1)).findById(passenger.getId());
        verify(passangerRepository, times(1)).save(passenger);

    }

    @Test
    void createListPassenger() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);
        PassangerServices passengerService = new PassangerServices(passangerRepository);

        Passenger passenger1 = new Passenger(1,"12345678", "John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), 1234566, reservation, ticket);
        Passenger passenger2 = new Passenger(2,"98765432", "Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), 9876543,reservation, ticket);
        List<Passenger> passengers = Arrays.asList(passenger1, passenger2);

        passengerService.createListPassenger(passengers);

        verify(passangerRepository, times(1)).saveAll(passengers);
    }

    @Test
    void cantCreateListPassenger() {


        List<Passenger> passengers = Arrays.asList();

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createListPassenger(passengers));

        assertEquals("Passenger is required and cannot be empty", e.getMessage());

    }

    @Test
    void passengerAlreadyCreated() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger existingPassenger = new Passenger(1,"12345678", "John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), 1234566, reservation, ticket);


        List<Passenger> passengers =Arrays.asList(new Passenger(1,"12345678", "John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), 1234566, reservation, ticket),
        (new Passenger(2,"12345678", "Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), 9876543,reservation, ticket)));

        when(passangerRepository.findByDni("12345678")).thenReturn(Optional.of(existingPassenger));

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createListPassenger(passengers));

        assertEquals("The passenger has already been created", e.getMessage());

        verify(passangerRepository, never()).saveAll(passengers);


    }

    @Test
    void passengerListWithNameNull() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger1 = new Passenger(1,"12345678", null, "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), 1234566, reservation, ticket);
        Passenger passenger2 = new Passenger(2,"98765432", null, "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), 9876543,reservation, ticket);

        List<Passenger> passengers = Arrays.asList(passenger1, passenger2);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createListPassenger(passengers));

        assertEquals("Name is required", e.getMessage());

    }

    @Test
    void passengerListWithLastNameNull() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger1 = new Passenger(1,"12345678", "Jhon", null, "john.doe@example.com", LocalDate.of(1990, 1, 1), 1234566, reservation, ticket);
        Passenger passenger2 = new Passenger(2,"98765432", "Jay", null, "jane.doe@example.com", LocalDate.of(1995, 2, 2), 9876543,reservation, ticket);

        List<Passenger> passengers = Arrays.asList(passenger1, passenger2);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createListPassenger(passengers));

        assertEquals("Last name is required", e.getMessage());


    }

    @Test
    void passengerListWithDniNull() {
        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger1 = new Passenger(1,null, "Jhon", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), 1234566, reservation, ticket);
        Passenger passenger2 = new Passenger(2,null, "Jay", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), 9876543,reservation, ticket);

        List<Passenger> passengers = Arrays.asList(passenger1, passenger2);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createListPassenger(passengers));

        assertEquals("Dni is required", e.getMessage());
    }

    @Test
    void passengerListWithInvalidDni() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger1 = new Passenger(1,"sasas", "Jhon", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), 1234566, reservation, ticket);
        Passenger passenger2 = new Passenger(2,"mamama", "Jay", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), 9876543,reservation, ticket);

        List<Passenger> passengers = Arrays.asList(passenger1, passenger2);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createListPassenger(passengers));

        assertEquals("Dni must be numeric", e.getMessage());

    }

    @Test
    void passengerListWithEmailNull(){
        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger1 = new Passenger(1, "123456", "Jhon", "Doe", null, LocalDate.of(1990, 1, 1), 1234566, reservation, ticket);
        Passenger passenger2 = new Passenger(2, "987653", "Jay", "Doe", null, LocalDate.of(1995, 2, 2), 9876543, reservation, ticket);

        List<Passenger> passengers = Arrays.asList(passenger1, passenger2);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createListPassenger(passengers));

        assertEquals("Email is required", e.getMessage());
    }

    @Test
    void passengerListWithInvalidEmail() {

        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger1 = new Passenger(1,"123456", "Jhon", "Doe", "john.doe.com", LocalDate.of(1990, 1, 1), 1234566, reservation, ticket);
        Passenger passenger2 = new Passenger(2,"987654", "Jay", "Doe", "jane.doee.com", LocalDate.of(1995, 2, 2), 9876543,reservation, ticket);

        List<Passenger> passengers = Arrays.asList(passenger1, passenger2);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createListPassenger(passengers));

        assertEquals("Invalid email, validate that it is well written", e.getMessage());

    }

    @Test
    void passengerListWithBirthDateNull() {


            Reservation reservation = mock(Reservation.class);
            Ticket ticket = mock(Ticket.class);

            Passenger passenger1 = new Passenger(1,"12345", "Jhon", "Doe", "john.doe@example.com", null, 1234566, reservation, ticket);
            Passenger passenger2 = new Passenger(2,"98765", "Jay", "Doe", "jane.doe@example.com", null, 9876543,reservation, ticket);

            List<Passenger> passengers = Arrays.asList(passenger1, passenger2);

            Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createListPassenger(passengers));

            assertEquals("Birth date is required", e.getMessage());

    }

    @Test
    void passengerListWithPhoneNull() {
        Reservation reservation = mock(Reservation.class);
        Ticket ticket = mock(Ticket.class);

        Passenger passenger1 = new Passenger(1,"123456", "Jhon", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), null, reservation, ticket);
        Passenger passenger2 = new Passenger(2,"987654", "Jay", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), null,reservation, ticket);

        List<Passenger> passengers = Arrays.asList(passenger1, passenger2);

        Exception e = assertThrows(GeneralException.class, () -> this.passangerServices.createListPassenger(passengers));

        assertEquals("Phone number is required", e.getMessage());
    }
}
