package com.flight.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flight.entity.FlightEntity;
import com.flight.service.FlightService;

@RestController
public class FlightController {
	
	
	@Autowired
	private FlightService flightService;

	@GetMapping("hello")
	public String helloWorld() {
		return "Hello World";
	}
	
	@PostMapping("addFlight")
	public FlightEntity addFlight(@RequestBody FlightEntity flightEntity) {
		flightEntity= flightService.saveFlight(flightEntity);
		System.out.println(flightEntity.getId());
		return flightEntity;
	}
	
	@DeleteMapping("removeFlight/{id}")
	public String removeFlight(@PathVariable("id") Integer id) {
		flightService.removeFlight(id);
		return "Flight ID="+id+" deleted successfully";
	}
	
	@PutMapping("updateFlight")
	public FlightEntity updateFlight(@Valid @RequestBody FlightEntity flightEntity){
		flightEntity= flightService.updateFlight(flightEntity);
		return flightEntity;
	}
	
	@GetMapping("getFlightPrice/{id}")
	public FlightEntity getFlightPrice(@PathVariable("id") Integer id) {
		FlightEntity flightEntity=flightService.getFlightPrice(id);
		return flightEntity;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleException(MethodArgumentNotValidException exception) {
		Map<String, String> messages=new HashMap<>();
		exception.getAllErrors().forEach(error->{
			String fieldName=((FieldError)error).getField();
			String errorMessage=((FieldError)error).getDefaultMessage();
			messages.put(fieldName, errorMessage);
		});
		return messages;
	}
}
