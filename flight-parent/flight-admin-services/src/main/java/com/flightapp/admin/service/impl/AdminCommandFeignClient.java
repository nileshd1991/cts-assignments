package com.flightapp.admin.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.flightapp.models.Airline;
import com.flightapp.models.Flight;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "FLIGHT-MANAGEMENT-ADMIN-COMMAND")
public interface AdminCommandFeignClient {

	@PostMapping("/airline/register")
	ResponseEntity<Airline> registerAirline(@RequestBody Airline body);
	
	@PutMapping("/airline/update/airline/{airline_id}")
	ResponseEntity<Airline> updateAirline(@PathVariable("airline_id") Integer airlineId, @RequestBody Airline body);

	@PostMapping("/airline/inventory/add")
	ResponseEntity<Flight> addAirlineInventory(@RequestBody Flight body);
	
	@PutMapping("/airline/inventory/update/flight/{flight_id}")
	ResponseEntity<Flight> updateAirlineInventory(@PathVariable("flight_id") Integer flightId,@RequestBody Flight body);

	@PutMapping("/airline/block/{airline_id}")
	ResponseEntity<Void> blockAirline(@PathVariable("airline_id") Integer airlineId);

	@PutMapping("/airline/unblock/{airline_id}")
	ResponseEntity<Void> unBlockAirline(@PathVariable("airline_id") Integer airlineId);
	
	@PutMapping("/airline/flight/{flight_id}/seat/{seat_type}/{no_of_seats}")
	ResponseEntity<Void> updateSeatQuota(@PathVariable("flight_id") Integer flightId,@PathVariable("seat_type") String seatType,@PathVariable("no_of_seats") Integer noOfSeats);

}
