package com.flight.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flight.beans.FlightSearchBean;
import com.flight.entity.FlightEntity;

@Service
public interface FlightService {
	public FlightEntity saveFlight(FlightEntity flightEntity);

	public void removeFlight(Integer id);

	public FlightEntity updateFlight(FlightEntity flightEntity);

	public FlightEntity getFlightPrice(Integer id);

	public List<FlightEntity> searchFlights(FlightSearchBean flightSearchBean);
}
