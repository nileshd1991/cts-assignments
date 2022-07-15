package com.flightapp.admin.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.admin.service.UserService;
import com.flightapp.admin.service.exceptions.FlightApiException;
import com.flightapp.models.BookFlightTicket;
import com.flightapp.models.BookFlightTicket.MealTypeEnum;
import com.flightapp.models.BookFlightTicket.SeatTypeEnum;
import com.flightapp.models.BookedFlightDetails;
import com.flightapp.models.PassengerDetails;
import com.flightapp.models.SearchFlight;
import com.flightapp.models.SearchFlightResponse;
import com.flightapp.schema.model.FlightEntity;
import com.flightapp.schema.model.PassengerDetail;
import com.flightapp.schema.model.TicketBooking;
import com.flightapp.schema.repository.FlightRepository;
import com.flightapp.schema.repository.PassengerDetailRepository;
import com.flightapp.schema.repository.TicketBookingRepository;
import com.flightapp.util.config.PnrGenerationUtility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private PnrGenerationUtility pnrGenerationUtility;

	@Autowired
	private TicketBookingRepository ticketBookingRepository;

	@Autowired
	private PassengerDetailRepository passengerDetailRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AdminCommandFeignClient adminCommandFeignClient;

	@Override
	public ResponseEntity<List<SearchFlightResponse>> searchFlight(SearchFlight body) {
		Timestamp startDate = new Timestamp(body.getStartTime());
		Timestamp returnDate = new Timestamp(body.getReturnTime());
		checkDateValidation(startDate,returnDate);
		List<FlightEntity> list = flightRepository.searchFlights(body.getWayType().toString(),
				body.getFromLocation(), body.getToLocation(), startDate, returnDate);
		List<SearchFlightResponse> searchedFlights = new ArrayList<SearchFlightResponse>();
		SearchFlightResponse flight = null;
		for (FlightEntity flightEntity : list) {
			flight = modelMapper.map(flightEntity, SearchFlightResponse.class);
			flight.setAirlineId(flightEntity.getAirline().getAirlineId());
			flight.setAirlineName(flightEntity.getAirline().getName());
			searchedFlights.add(flight);
		}
		return getsearchFlightResponse(searchedFlights);
	}

	private void checkDateValidation(Timestamp startDate, Timestamp returnDate) {
		Instant startDateInstant=startDate.toInstant();
		Instant returnDateInstant=returnDate.toInstant();
		Instant now = Instant.now();
		if(returnDateInstant.isBefore(startDateInstant)) {
			throw new FlightApiException("Start date should be before return date", HttpStatus.BAD_GATEWAY);
		}
		else if(startDateInstant.isBefore(now)||returnDateInstant.isBefore(now)) {
			throw new FlightApiException("Please enter valid future dates", HttpStatus.BAD_GATEWAY);
		}
		
	}

	private ResponseEntity<List<SearchFlightResponse>> getsearchFlightResponse(
			List<SearchFlightResponse> searchedFlights) {
		ResponseEntity<List<SearchFlightResponse>> response = new ResponseEntity<List<SearchFlightResponse>>(
				searchedFlights, HttpStatus.OK);
		return response;
	}

	@Override
	public ResponseEntity<BookFlightTicket> bookTicket(Integer flightId, @Valid BookFlightTicket bookFlightTicket) {
		Optional<FlightEntity> flightEntityOptional = flightRepository.findById(flightId);
		if (flightEntityOptional.isPresent()) {
			BookFlightTicket flightTicket = getTicketBooking(flightEntityOptional.get(), bookFlightTicket);
			return new ResponseEntity<BookFlightTicket>(flightTicket, HttpStatus.OK);
		} else {
			throw new FlightApiException("Invalid flight Id", HttpStatus.NOT_FOUND);
		}
	}

	private BookFlightTicket getTicketBooking(FlightEntity flightEntity, BookFlightTicket bookFlightTicket) {
		TicketBooking ticket = new TicketBooking();
		Double totalCost = getTotalTicketCost(flightEntity, bookFlightTicket.getSeatType().getValue(),
				bookFlightTicket.getPassengerList().size());
		ticket.setTotalCost(totalCost);
		bookFlightTicket.setTotalCost(totalCost);
		ticket.setFlight(flightEntity);
		ticket.setUserName(bookFlightTicket.getUserName());
		ticket.setEmail(bookFlightTicket.getEmail());
		ticket.setSeatType(bookFlightTicket.getSeatType().getValue());
		ticket.setMealType(bookFlightTicket.getMealType().getValue());
		String pnr = pnrGenerationUtility.generatePNRNumber();
		ticket.setPnr(pnr);
		bookFlightTicket.setPnr(pnr);
		ticket.setCancelled(false);
		ticket = ticketBookingRepository.save(ticket);
		bookFlightTicket.setTicketId(ticket.getTicketId());
		resetFlightSeatsQuota(flightEntity, bookFlightTicket);
		savePassengerDetails(ticket, bookFlightTicket.getPassengerList());
		setBookedFlightDetails(bookFlightTicket, flightEntity);
		return bookFlightTicket;
	}

	private void resetFlightSeatsQuota(FlightEntity flightEntity, BookFlightTicket bookFlightTicket) {
		if (bookFlightTicket.getSeatType().getValue().equals(SeatTypeEnum.NON_BUSINESS_CLASS.getValue())) {
			Integer seats = flightEntity.getNonBusinessClassSeats();
			flightEntity.setNonBusinessClassSeats(seats - bookFlightTicket.getPassengerList().size());
			flightEntity = flightRepository.save(flightEntity);
			adminCommandFeignClient.updateSeatQuota(flightEntity.getFlightId(), SeatTypeEnum.NON_BUSINESS_CLASS.getValue(), flightEntity.getNonBusinessClassSeats());
			
		} else {
			Integer seats = flightEntity.getBusinessClassSeats();
			flightEntity.setBusinessClassSeats(seats - bookFlightTicket.getPassengerList().size());
			flightEntity = flightRepository.save(flightEntity);
			adminCommandFeignClient.updateSeatQuota(flightEntity.getFlightId(), SeatTypeEnum.BUSINESS_CLASS.getValue(), flightEntity.getBusinessClassSeats());
		}
	}

	private Double getTotalTicketCost(FlightEntity flightEntity, String seatType, int size) {
		if (seatType.equals(SeatTypeEnum.NON_BUSINESS_CLASS.getValue())) {
			Double cost = flightEntity.getNonBusinessClassCost();
			return cost * size;
		} else {
			Double cost = flightEntity.getBusinessClassCost();
			return cost * size;
		}
	}

	private void setBookedFlightDetails(BookFlightTicket bookFlightTicket, FlightEntity flightEntity) {
		BookedFlightDetails bookedFlightDetails = mapper.map(flightEntity, BookedFlightDetails.class);
		bookedFlightDetails.setAirlineId(flightEntity.getAirline().getAirlineId());
		bookedFlightDetails.setAirlineName(flightEntity.getAirline().getName());
		bookFlightTicket.setBookedFlightDetails(bookedFlightDetails);
	}

	private void savePassengerDetails(TicketBooking ticket, List<PassengerDetails> passengerList) {
		List<PassengerDetail> details = new ArrayList<>();

		for (PassengerDetails passenger : passengerList) {
			getPassengerDetails(details, passenger, ticket);
		}
		ticket.setPassengerDetails(details);
	}

	private void getPassengerDetails(List<PassengerDetail> details, PassengerDetails passenger, TicketBooking ticket) {
		PassengerDetail passengerEntity = new PassengerDetail();
		passengerEntity.setName(passenger.getName());
		passengerEntity.setGender(passenger.getGender());
		passengerEntity.setAge(passenger.getAge());
		passengerEntity.setTicketBooking(ticket);
		passengerEntity = passengerDetailRepository.save(passengerEntity);
		details.add(passengerEntity);
	}

	@Override
	public ResponseEntity<BookFlightTicket> getTicketDetails(String pnr) {
		Optional<TicketBooking> ticketOpt = ticketBookingRepository.findByPnr(pnr);
		if (ticketOpt.isPresent()) {
			TicketBooking ticket = ticketOpt.get();
			BookFlightTicket bookFlightTicket = mapper.map(ticket, BookFlightTicket.class);
			bookFlightTicket.seatType(SeatTypeEnum.fromValue(ticket.getSeatType()));
			bookFlightTicket.mealType(MealTypeEnum.fromValue(ticket.getMealType()));
			setBookedFlightDetails(bookFlightTicket, ticket.getFlight());
			setPassengerDetails(bookFlightTicket, ticket.getPassengerDetails());
			return new ResponseEntity<BookFlightTicket>(bookFlightTicket, HttpStatus.OK);
		} else {
			throw new FlightApiException("Invalid PNR", HttpStatus.NOT_FOUND);
		}
	}

	private void setPassengerDetails(BookFlightTicket bookFlightTicket, List<PassengerDetail> passengerDetails) {
		List<PassengerDetails> details = new ArrayList<>();
		passengerDetails.forEach(p -> details.add(modelMapper.map(p, PassengerDetails.class)));
		bookFlightTicket.setPassengerList(details);
	}

	@Override
	public ResponseEntity<List<BookFlightTicket>> getTicketBookingHistory(String emailId) {
		List<BookFlightTicket> ticketBookingHistory = new ArrayList<>();
		List<TicketBooking> list = ticketBookingRepository.findAllByEmail(emailId);
		for (TicketBooking ticket : list) {
			BookFlightTicket bookFlightTicket = mapper.map(ticket, BookFlightTicket.class);
			bookFlightTicket.seatType(SeatTypeEnum.fromValue(ticket.getSeatType()));
			bookFlightTicket.mealType(MealTypeEnum.fromValue(ticket.getMealType()));
			setBookedFlightDetails(bookFlightTicket, ticket.getFlight());
			setPassengerDetails(bookFlightTicket, ticket.getPassengerDetails());
			ticketBookingHistory.add(bookFlightTicket);
		}
		return new ResponseEntity<List<BookFlightTicket>>(ticketBookingHistory, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> cancelFlightTicket(String pnr) {
		Optional<TicketBooking> ticketOpt = ticketBookingRepository.findByPnr(pnr);
		if (ticketOpt.isPresent()) {
			TicketBooking ticket = ticketOpt.get();
			FlightEntity flightEntity=ticket.getFlight();
			
			if(checkCancellationValidation(flightEntity.getStartTime())) {
				ticket.setCancelled(true);
				ticketBookingRepository.save(ticket);
				Integer noOfSeatsOfTicket=ticket.getPassengerDetails().size();
				
				String seatType=ticket.getSeatType();
				Integer noOfSeatsToSet=0;
				if(seatType.equals(SeatTypeEnum.BUSINESS_CLASS.getValue())) {
					noOfSeatsToSet=flightEntity.getBusinessClassSeats()+noOfSeatsOfTicket;
					flightEntity.setBusinessClassSeats(noOfSeatsToSet);
				}
				else {
					noOfSeatsToSet=flightEntity.getNonBusinessClassSeats()+noOfSeatsOfTicket;
					flightEntity.setNonBusinessClassSeats(noOfSeatsToSet);
				}
				flightRepository.save(flightEntity);
				adminCommandFeignClient.updateSeatQuota(flightEntity.getFlightId(), seatType, noOfSeatsToSet);
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
			else {
				throw new FlightApiException("Ticket cancellation time is over", HttpStatus.BAD_REQUEST);
			}
			
		} else {
			throw new FlightApiException("Invalid PNR", HttpStatus.NOT_FOUND);
		}
	}

	private boolean checkCancellationValidation(Date date) {
		Instant now = Instant.now();
		Instant _24_hrsBefore =  date.toInstant().minus(24,ChronoUnit.HOURS);
		return now.isBefore(_24_hrsBefore);
	}

}
