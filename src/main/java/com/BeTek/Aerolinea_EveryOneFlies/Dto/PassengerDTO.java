package com.BeTek.Aerolinea_EveryOneFlies.Dto;

import java.time.LocalDate;

public class PassengerDTO {

    private String dni;
    private String name;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private Integer numbeCel;

    public PassengerDTO(String dni, String name, String lastName, String email, LocalDate birthDate, Integer numbeCel) {
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.numbeCel = numbeCel;
    }

    public PassengerDTO() {
    }


    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getNumbeCel() {
        return numbeCel;
    }
}
