package com.BeTek.Aerolinea_EveryOneFlies.Repositories;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
