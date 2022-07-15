package com.flightapp.admin.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.models.Airline;
import com.flightapp.models.Flight;

@Service
public interface AirlineService {
	ResponseEntity<Airline> registerAirline(@Valid Airline body);

	ResponseEntity<Flight> addAirlineInventory(Flight body);

	ResponseEntity<List<Airline>> getAirlines();

	ResponseEntity<Void> blockAirline(Integer airlineId);

	ResponseEntity<Void> unBlockAirline(Integer airlineId);

	ResponseEntity<Flight> getAirlineInvenroty(Integer flightId);

	ResponseEntity<Flight> updateAirlineInventory(Integer flightId, @Valid Flight flight);

	ResponseEntity<Airline> getAirline(Integer airlineId);

	ResponseEntity<Airline> updateAirline(Integer airlineId, @Valid Airline airline);

	ResponseEntity<List<Flight>> getAirlineInventoryList(Integer airlineId);
}
