package com.BeTek.Aerolinea_EveryOneFlies.Repositories;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepository extends JpaRepository<Fare, Integer> {
}
