package com.BeTek.Aerolinea_EveryOneFlies.Repositories;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassangerRepository extends JpaRepository<Passenger, Integer> {
    Optional<Passenger> findByDni(String dni);
}
