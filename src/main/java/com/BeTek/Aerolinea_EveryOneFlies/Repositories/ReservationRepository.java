package com.BeTek.Aerolinea_EveryOneFlies.Repositories;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
