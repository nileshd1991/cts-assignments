package com.flightapp.schema.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.schema.model.TicketBooking;

@Repository
public interface TicketBookingRepository extends JpaRepository<TicketBooking, Integer>{

	boolean existsByPnr(String pnr);

	Optional<TicketBooking> findByPnr(String pnr);

	List<TicketBooking> findAllByEmail(String emailId);

}
