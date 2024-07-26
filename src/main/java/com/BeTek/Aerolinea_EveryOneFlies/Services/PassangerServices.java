package com.BeTek.Aerolinea_EveryOneFlies.Services;

import com.BeTek.Aerolinea_EveryOneFlies.Exceptions.GeneralException;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import com.BeTek.Aerolinea_EveryOneFlies.Repositories.PassangerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PassangerServices {

  private final PassangerRepository passangerRepository;

  @Autowired
  public PassangerServices(PassangerRepository passangerRepository) {
    this.passangerRepository = passangerRepository;
  }

  public void createPassenger(Passenger passenger){

    if(Objects.isNull(passenger.getDni())){
      throw new GeneralException("Dni is required");
    }else {
      String cleanDni = passenger.getDni().replaceAll("\\s+", "");
      passenger.setDni(cleanDni);
      if (!cleanDni.matches("\\d+")) {
        throw new GeneralException("Dni must be numeric");
      }
    }
      /*
      \\s -> expresión regular que para econtrar todos los espacios en blanco
      \\d -> expresion regular sirve para identificar dígitos del 0 al 9.
      + -> Cuantificador que significa "uno o mas"
      "uno o más caracteres de espacio en blanco consecutivos".
       */

    if(Objects.isNull(passenger.getName()) || passenger.getName().trim().isEmpty()){
      throw new GeneralException("Name is required");
    }
    if(Objects.isNull(passenger.getLastName()) || passenger.getLastName().trim().isEmpty()){
      throw new GeneralException("Last name is required");
    }
    if(Objects.isNull(passenger.getEmail()) || passenger.getEmail().trim().isEmpty()){
      throw new GeneralException("Email is required");
    }else {
      //Pattern -> manejo de expresiones regulaes
      //Matcher -> operaciones de coincidencia
      Pattern pattern = Pattern.compile("^[_A-Za-z0-9\\+]+.(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
      Matcher matcher = pattern.matcher(passenger.getEmail());
      boolean result = matcher.find();
      if(!result){
        throw new GeneralException("Invalid email, validate that it is well written");
      }
      if(Objects.isNull(passenger.getBirthDate())){
        throw new GeneralException("birth date is required");
      }
      if(Objects.isNull(passenger.getNumberCel())){
        throw new GeneralException("phone number is required");
      }
    }
    this.passangerRepository.save(passenger);
  }

  // CREA LISTA DE PASAJEROS TRAIDA DESDE SERVICE
  public void createListPassenger(List<Passenger> passengers){
    if (passengers == null || passengers.isEmpty()) {
      throw new GeneralException("Passenger is required and cannot be empty");
    }

    Boolean passExist = passengerExist(passengers);
    if(passExist){
      throw new GeneralException("The passenger has already been created");
    } else {
      for (Passenger passenger : passengers) {

        // Validate DNI
        if (Objects.isNull(passenger.getDni())) {
          throw new GeneralException("Dni is required");
        } else {
          String cleanDni = passenger.getDni().replaceAll("\\s+", "");
          passenger.setDni(cleanDni);
          if (!cleanDni.matches("\\d+")) {
            throw new GeneralException("Dni must be numeric");
          }
        }

        // Validate Name
        if (Objects.isNull(passenger.getName()) || passenger.getName().trim().isEmpty()) {
          throw new GeneralException("Name is required");
        }

        // Validate Last Name
        if (Objects.isNull(passenger.getLastName()) || passenger.getLastName().trim().isEmpty()) {
          throw new GeneralException("Last name is required");
        }

        // Validate Email
        if (Objects.isNull(passenger.getEmail()) || passenger.getEmail().trim().isEmpty()) {
          throw new GeneralException("Email is required");
        } else {
          Pattern pattern = Pattern.compile("^[_A-Za-z0-9\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
          Matcher matcher = pattern.matcher(passenger.getEmail());
          boolean result = matcher.find();
          if (!result) {
            throw new GeneralException("Invalid email, validate that it is well written");
          }
        }

        // Validate Birth Date
        if (Objects.isNull(passenger.getBirthDate())) {
          throw new GeneralException("Birth date is required");
        }

        // Validate Phone Number
        if (Objects.isNull(passenger.getNumberCel())) {
          throw new GeneralException("Phone number is required");
        }
      }

      this.passangerRepository.saveAll(passengers);
    }

  }

  //Verifica si el pasajero existe en la lista
  public Boolean passengerExist(List<Passenger> passengers) {
    for (Passenger passenger : passengers) {
      Optional<Passenger> passengerOptional = passangerRepository.findByDni(passenger.getDni());
      if (passengerOptional.isPresent()) {
        return true; // Si se encuentra un pasajero existente, devuelve true
      }
    }
    return false; // Si no se encuentra ningún pasajero existente, devuelve false
  }

  public Passenger getPassenger(Integer id) {
    Optional<Passenger> passengerOptional = this.passangerRepository.findById(id);
    if (passengerOptional.isPresent()) {
      return passengerOptional.get();
    }
    throw new GeneralException("No exist passenger with id " + id);
  }

  public List<Passenger> getAllPassengers(){

    return this.passangerRepository.findAll();
  }

  public void deletePassenger(Integer id){
    Optional<Passenger> passengerOptional = this.passangerRepository.findById(id);
    if(passengerOptional.isEmpty()){
      throw new GeneralException("Passenger no exist");
    }
    this.passangerRepository.delete(passengerOptional.get());
  }

  public Passenger updatePassenger(Integer id, Passenger passenger) {
    Optional<Passenger> passengerOptional = this.passangerRepository.findById(id);
    if (passengerOptional.isPresent()) {
      Passenger passengerToUpdate = passengerOptional.get();

      if (passenger.getBirthDate() != null) {
        passengerToUpdate.setBirthDate(passenger.getBirthDate());
      }

      if (passenger.getDni() != null) {
        passengerToUpdate.setDni(passenger.getDni());
      }

      if (passenger.getEmail() != null) {
        passengerToUpdate.setEmail(passenger.getEmail());
      }

      if (passenger.getName() != null) {
        passengerToUpdate.setName(passenger.getName());
      }

      if (passenger.getLastName() != null) {
        passengerToUpdate.setLastName(passenger.getLastName());
      }

      if (passenger.getNumberCel() != null) {
        passengerToUpdate.setNumberCel(passenger.getNumberCel());
      }

      return passangerRepository.save(passengerToUpdate);
    } else {
      throw new GeneralException("The passenger no exist with id " + id);
    }
  }
  public Optional<Passenger> findPassengerByDni(String dni) {
    return passangerRepository.findByDni(dni);
  }


}
