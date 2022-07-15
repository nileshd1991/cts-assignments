package com.flightapp.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import com.flightapp.admin.service.exceptions.FlightApiException;
import com.flightapp.admin.service.impl.AdminCommandFeignClient;
import com.flightapp.admin.service.impl.UserServiceImpl;
import com.flightapp.models.SearchFlight;
import com.flightapp.models.SearchFlight.WayTypeEnum;
import com.flightapp.models.SearchFlightResponse;
import com.flightapp.schema.model.AirlineEntity;
import com.flightapp.schema.model.FlightEntity;
import com.flightapp.schema.repository.FlightRepository;
import com.flightapp.schema.repository.PassengerDetailRepository;
import com.flightapp.schema.repository.TicketBookingRepository;
import com.flightapp.util.config.PnrGenerationUtility;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@Spy
	ModelMapper modelMapper=new ModelMapper();
	
	@Mock
	private FlightRepository flightRepository;

	@Mock
	private PnrGenerationUtility pnrGenerationUtility;

	@Mock
	private TicketBookingRepository ticketBookingRepository;

	@Mock
	private PassengerDetailRepository passengerDetailRepository;

	@Mock
	private ModelMapper mapper;
	
	@Mock
	private AdminCommandFeignClient adminCommandFeignClient;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	
	@Test(expected = FlightApiException.class)
	public void searchFlightTest_StartDateBeforeEndDateException() {
		SearchFlight body=new SearchFlight();
		body.setFromLocation("Mumbai");
		body.setToLocation("Pune");
		body.setStartTime(1656477000000L);
		body.setReturnTime(1656390600000L);
		body.setWayType(WayTypeEnum.ONE_WAY);
		
		List<FlightEntity> list=new ArrayList<>();
		FlightEntity f1=new FlightEntity();
		AirlineEntity a1=new AirlineEntity();
		a1.setActive(true);
		a1.setAddress("Pune");
		a1.setAirlineId(1);
		a1.setContact("8787878787");
		a1.setName("Vistara");
		f1.setAirline(a1);
		f1.setBusinessClassCost(5000d);
		f1.setBusinessClassSeats(50);
		f1.setName("F000");
		f1.setNonBusinessClassCost(3000d);
		f1.setNonBusinessClassSeats(60);
		f1.setFromLocation("Mumbai");
		f1.setToLocation("Pune");
		f1.setWayType("One way");
		f1.setStartTime(new Date(1656477000000L));
		f1.setReturnTime(new Date(1656390600000L));
		list.add(f1);
		
//		when(flightRepository.searchFlights(anyString(), anyString(), anyString(), any(Timestamp.class), any(Timestamp.class))).thenReturn(list);
		
		userService.searchFlight(body);
		

	}
	
	@Test
	public void searchFlightTest() {
		SearchFlight body=new SearchFlight();
		body.setFromLocation("Mumbai");
		body.setToLocation("Pune");
		body.setStartTime(1657859400000L);
		body.setReturnTime(1657945800000L);
		body.setWayType(WayTypeEnum.ONE_WAY);
		
		List<FlightEntity> list=new ArrayList<>();
		FlightEntity f1=new FlightEntity();
		AirlineEntity a1=new AirlineEntity();
		a1.setActive(true);
		a1.setAddress("Pune");
		a1.setAirlineId(1);
		a1.setContact("8787878787");
		a1.setName("Vistara");
		f1.setAirline(a1);
		f1.setBusinessClassCost(5000d);
		f1.setBusinessClassSeats(50);
		f1.setName("F000");
		f1.setNonBusinessClassCost(3000d);
		f1.setNonBusinessClassSeats(60);
		f1.setFromLocation("Mumbai");
		f1.setToLocation("Pune");
		f1.setWayType("One way");
		f1.setStartTime(new Date(1657859400000L));
		f1.setReturnTime(new Date(1657945800000L));
		list.add(f1);
		
		when(flightRepository.searchFlights(anyString(), anyString(), anyString(), any(Timestamp.class), any(Timestamp.class))).thenReturn(list);
		
		ResponseEntity<List<SearchFlightResponse>> searchFlightResponse = userService.searchFlight(body);
		
		assertEquals(searchFlightResponse.getBody().size(), 1);
		assertEquals(searchFlightResponse.getBody().get(0).getName(), "F000");

	}
}
