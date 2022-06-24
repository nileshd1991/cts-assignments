package com.flight.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flight.beans.FlightSearchBean;
import com.flight.entity.FlightEntity;
import com.flight.service.FlightService;

@RestController
public class FlightSearchController {
	
	
	@Autowired
	private FlightService flightService;

	@PostMapping("flight/search")
	public List<FlightEntity> searchFlight(@RequestBody FlightSearchBean flightSearchBean ) {
		List<FlightEntity> flightList = flightService.searchFlights(flightSearchBean);
		return flightList;
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
