package com.flightapp.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.admin.service.AirlineService;
import com.flightapp.admin.service.exceptions.FlightApiException;
import com.flightapp.models.Airline;
import com.flightapp.models.Flight;
import com.flightapp.models.Flight.WayTypeEnum;
import com.flightapp.schema.model.AirlineEntity;
import com.flightapp.schema.model.FlightEntity;
import com.flightapp.schema.repository.AirlineRepository;
import com.flightapp.schema.repository.FlightRepository;

@Service
@EnableFeignClients
public class AirlineServiceImpl implements AirlineService{
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AirlineRepository airlineRepository;
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private AdminCommandFeignClient adminCommandFeignClient;
	
//	@Override
//	public ResponseEntity<Airline> registerAirline(@Valid Airline body) {
//		try {
//			AirlineEntity entity=modelMapper.map(body, AirlineEntity.class);
//			entity.setActive(true);
//			entity=airlineRepository.save(entity);
//			ResponseEntity<Airline> response=getAddAirLineResponse(entity,body);
//			return response;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new FlightApiException(e.getMessage(), e, HttpStatus.BAD_REQUEST);
//		}
//	}

//	private ResponseEntity<Airline> getAddAirLineResponse(AirlineEntity entity, Airline body) {
//		body.setId(entity.getAirlineId());
//		ResponseEntity<Airline> responseEntity=new ResponseEntity<Airline>(body, HttpStatus.OK);
//		return responseEntity;
//	}

//	@Override
//	public ResponseEntity<Flight> addAirlineInventory(Flight body) {
//		try {
//			FlightEntity flightEntity = modelMapper.map(body, FlightEntity.class);
//			flightEntity=flightRepository.save(flightEntity);
//			return getaddAirlineInventoryResponse(flightEntity,body);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new FlightApiException(e.getMessage(), e, HttpStatus.BAD_REQUEST);
//		}
//	}

//	private ResponseEntity<Flight> getaddAirlineInventoryResponse(FlightEntity flightEntity, Flight body) {
//		body.setId(flightEntity.getFlightId());
//		ResponseEntity<Flight> responseEntity=new ResponseEntity<Flight>(body, HttpStatus.OK);
//		return responseEntity;
//	}

	@Override
	public ResponseEntity<List<Airline>> getAirlines() {
		try {
			List<AirlineEntity> airlines = airlineRepository.findAll();
			ResponseEntity<List<Airline>> response=getReponse(airlines);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FlightApiException(e.getMessage(), e, HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<List<Airline>> getReponse(List<AirlineEntity> airlines) {
		List<Airline> airlinesList=new ArrayList<Airline>();
		Airline airline=null;
		for (AirlineEntity airlineEntity : airlines) {
			airline=modelMapper.map(airlineEntity, Airline.class);
			airlinesList.add(airline);
		}
		ResponseEntity<List<Airline>> response=new ResponseEntity<List<Airline>>(airlinesList, HttpStatus.OK);
		return response;
	}

	@Override
	public ResponseEntity<Airline> registerAirline(@Valid Airline body) {
		return adminCommandFeignClient.registerAirline(body);
	}

	@Override
	public ResponseEntity<Flight> addAirlineInventory(Flight body) {
		System.out.println(body.getFromLocation()+"=="+body.getAirlineId());
		return adminCommandFeignClient.addAirlineInventory(body);
	}

	@Override
	public ResponseEntity<Void> blockAirline(Integer airlineId) {
		return adminCommandFeignClient.blockAirline(airlineId);
	}

	@Override
	public ResponseEntity<Void> unBlockAirline(Integer airlineId) {
		return adminCommandFeignClient.unBlockAirline(airlineId);
	}

	@Override
	public ResponseEntity<Flight> getAirlineInvenroty(Integer flightId) {
		Optional<FlightEntity> opt = flightRepository.findById(flightId);
		if(opt.isPresent()) {
			FlightEntity entity=opt.get();
			Flight flight = modelMapper.map(entity, Flight.class);
			flight.setWayType(WayTypeEnum.fromValue(entity.getWayType()));
			return new ResponseEntity<Flight>(flight, HttpStatus.OK);
		}
		throw new FlightApiException("Invalid Flight Id", HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Flight> updateAirlineInventory(Integer flightId, @Valid Flight flight) {
		return adminCommandFeignClient.updateAirlineInventory(flightId, flight);
	}

	@Override
	public ResponseEntity<Airline> getAirline(Integer airlineId) {
		Optional<AirlineEntity> opt = airlineRepository.findById(airlineId);
		if(opt.isPresent()) {
			AirlineEntity entity = opt.get();
			Airline airline=modelMapper.map(entity, Airline.class);
			return new ResponseEntity<Airline>(airline, HttpStatus.OK);
		}
		throw new FlightApiException("Invalid Airline Id", HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Airline> updateAirline(Integer airlineId, @Valid Airline airline) {
		return adminCommandFeignClient.updateAirline(airlineId, airline);
	}

	@Override
	public ResponseEntity<List<Flight>> getAirlineInventoryList(Integer airlineId) {
		List<FlightEntity> list= flightRepository.findByAirlineId(airlineId);
		List<Flight> resList=new ArrayList<Flight>();
		for (FlightEntity entity : list) {
			Flight flight = modelMapper.map(entity, Flight.class);
			flight.setWayType(WayTypeEnum.fromValue(entity.getWayType()));
			resList.add(flight);
		}
		return new ResponseEntity<List<Flight>>(resList, HttpStatus.OK);
	}

//	@Override
//	public ResponseEntity<Void> blockAirline(Integer airlineId) {
//		try {
//			Optional<AirlineEntity> entityOpt = airlineRepository.findById(airlineId);
//			if(entityOpt.isPresent()) {
//				AirlineEntity airlineEntity = entityOpt.get();
//				airlineEntity.setActive(false);
//				airlineRepository.save(airlineEntity);
//				ResponseEntity<Void> response=new ResponseEntity<Void>(HttpStatus.OK);
//				return response;
//			}
//			else {
//				throw new FlightApiException("Resource not found", HttpStatus.NOT_FOUND);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new FlightApiException(e.getMessage(), e, HttpStatus.BAD_REQUEST);
//		}
//	}

//	@Override
//	public ResponseEntity<Void> unBlockAirline(Integer airlineId) {
//		try {
//			Optional<AirlineEntity> entityOpt = airlineRepository.findById(airlineId);
//			if(entityOpt.isPresent()) {
//				AirlineEntity airlineEntity = entityOpt.get();
//				airlineEntity.setActive(true);
//				airlineRepository.save(airlineEntity);
//				ResponseEntity<Void> response=new ResponseEntity<Void>(HttpStatus.OK);
//				return response;
//			} else {
//				throw new FlightApiException("Resource not found", HttpStatus.NOT_FOUND);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new FlightApiException(e.getMessage(), e, HttpStatus.BAD_REQUEST);
//		}
//	}

}
