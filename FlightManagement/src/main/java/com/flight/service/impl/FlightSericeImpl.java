package com.flight.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.flight.beans.FlightSearchBean;
import com.flight.entity.FlightEntity;
import com.flight.repository.FlightRepository;
import com.flight.service.FlightService;

@Service
public class FlightSericeImpl implements FlightService {
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public FlightEntity saveFlight(FlightEntity flightEntity) {
		flightEntity=flightRepository.save(flightEntity);
		return flightEntity;
	}

	@Override
	public void removeFlight(Integer id) {
		flightRepository.deleteById(id);
	}

	@Override
	public FlightEntity updateFlight(FlightEntity flightEntity) {
		FlightEntity flightEntityPersisted = flightRepository.findById(flightEntity.getId()).get();
		flightEntityPersisted=updateFlight(flightEntity,flightEntityPersisted);
		flightEntityPersisted = flightRepository.save(flightEntityPersisted);
		return flightEntityPersisted;
	}

	private FlightEntity updateFlight(FlightEntity flightEntity, FlightEntity flightEntityPersisted) {
		flightEntityPersisted.setFlightName(flightEntity.getFlightName());
		flightEntityPersisted.setAirlineName(flightEntity.getAirlineName());
		flightEntityPersisted.setPrice(flightEntity.getPrice());
		return flightEntityPersisted;
	}

	@Override
	public FlightEntity getFlightPrice(Integer id) {
		Optional<FlightEntity> flightEntityOpt = flightRepository.findById(id);
		if(flightEntityOpt.isPresent()) {
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> entity=new HttpEntity<>(headers);
			ResponseEntity<Double> result = restTemplate.exchange("http://localhost:8082/getPrice", HttpMethod.GET, entity, Double.class);
			if(result.hasBody()) {
				FlightEntity flightEntity = flightEntityOpt.get();
				flightEntity.setPrice(result.getBody());
				flightRepository.save(flightEntity);
				return flightEntity;
			}
			else {
				return null;
			}
		}
		
		return null;
	}

	@Override
	public List<FlightEntity> searchFlights(FlightSearchBean flightSearchBean) {
		List<FlightEntity> list= flightRepository.searchFlights(flightSearchBean.getWayType(),flightSearchBean.getFromLocation(),flightSearchBean.getToLocation(),flightSearchBean.getStartTime(),flightSearchBean.getReturnTime());
		return list;
	}

}
