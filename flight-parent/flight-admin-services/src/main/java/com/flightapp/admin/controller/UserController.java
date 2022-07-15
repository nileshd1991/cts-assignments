package com.flightapp.admin.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.flightapp.admin.service.AirlineService;
import com.flightapp.admin.service.UserService;
import com.flightapp.api.BookingApi;
import com.flightapp.api.FlightApi;
import com.flightapp.api.SearchApi;
import com.flightapp.api.TicketApi;
import com.flightapp.models.ApiError;
import com.flightapp.models.BookFlightTicket;
import com.flightapp.models.Flight;
import com.flightapp.models.SearchFlight;
import com.flightapp.models.SearchFlightResponse;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@ApiResponses(value = {
		@ApiResponse(responseCode = "401", description = "Unauthorized", content = {
				@Content(mediaType = "application/json", schema = @Schema(anyOf = ApiError.class)) }),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = {
				@Content(mediaType = "application/json", schema = @Schema(anyOf = ApiError.class)) }) })
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController implements SearchApi, BookingApi, TicketApi, FlightApi {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AirlineService airlineService;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(anyOf = SearchFlightResponse.class))) }) })
	@Override
	public ResponseEntity<List<SearchFlightResponse>> searchFlight(SearchFlight body) {
		return userService.searchFlight(body);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = BookFlightTicket.class)) }) })
	@Override
	public ResponseEntity<BookFlightTicket> bookTicket(Integer flightId, @Valid BookFlightTicket bookFlightTicket) {
		return userService.bookTicket(flightId, bookFlightTicket);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = BookFlightTicket.class)) }) })
	@Override
	public ResponseEntity<BookFlightTicket> getTicketDetails(String pnr) {
		return userService.getTicketDetails(pnr);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(anyOf = BookFlightTicket.class))) }) })
	@Override
	public ResponseEntity<List<BookFlightTicket>> getTicketBookingHistory(String emailId) {
		return userService.getTicketBookingHistory(emailId);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful") })
	@Override
	public ResponseEntity<Void> cancelFlightTicket(String pnr) {
		return userService.cancelFlightTicket(pnr);
	}
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = Flight.class)) }) })
	@Override
	public ResponseEntity<Flight> getAirlineInventory(Integer flightId) {
		return airlineService.getAirlineInvenroty(flightId);
	}

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return BookingApi.super.getRequest();
	}

}
