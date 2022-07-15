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
import com.flightapp.api.AirlineApi;
import com.flightapp.api.AirlinesApi;
import com.flightapp.api.BlockApi;
import com.flightapp.api.InventoryApi;
import com.flightapp.api.RegisterApi;
import com.flightapp.api.UnblockApi;
import com.flightapp.api.UpdateApi;
import com.flightapp.models.Airline;
import com.flightapp.models.ApiError;
import com.flightapp.models.Flight;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@ApiResponses(value = {
		@ApiResponse(responseCode = "401", description = "Unauthorized", content = {
				@Content(mediaType = "application/json", schema = @Schema(anyOf = ApiError.class)) }),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = {
				@Content(mediaType = "application/json", schema = @Schema(anyOf = ApiError.class)) }) })
@RequestMapping("/airline")
@CrossOrigin
public class AirlineController
		implements AirlinesApi, BlockApi, UnblockApi, RegisterApi, InventoryApi, UpdateApi, AirlineApi {

	@Autowired
	private AirlineService airlineService;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = Airline.class)) }) })
	@Override
	public ResponseEntity<Airline> registerAirline(@Valid Airline body) {
		return airlineService.registerAirline(body);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = Flight.class)) }) })
	@Override
	public ResponseEntity<Flight> addAirlineInventory(Flight body) {
		return airlineService.addAirlineInventory(body);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(anyOf = Airline.class))) }) })
	@Override
	public ResponseEntity<List<Airline>> getAirlines() {
		return airlineService.getAirlines();
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = Void.class)) }) })
	@Override
	public ResponseEntity<Void> blockAirline(Integer airlineId) {
		return airlineService.blockAirline(airlineId);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = Void.class)) }) })
	@Override
	public ResponseEntity<Void> unBlockAirline(Integer airlineId) {
		return airlineService.unBlockAirline(airlineId);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = Flight.class)) }) })
	@Override
	public ResponseEntity<Flight> updateAirlineInventory(Integer flightId, @Valid Flight flight) {
		return airlineService.updateAirlineInventory(flightId, flight);
	}

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return AirlinesApi.super.getRequest();
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = Airline.class)) }) })
	@Override
	public ResponseEntity<Airline> updateAirline(Integer airlineId, @Valid Airline airline) {
		return airlineService.updateAirline(airlineId, airline);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = Airline.class)) }) })
	@Override
	public ResponseEntity<Airline> getAirline(Integer airlineId) {
		return airlineService.getAirline(airlineId);
	}
	
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(anyOf = Flight.class))) }) })
	@Override
	public ResponseEntity<List<Flight>> getAirlineInventoryList(Integer airlineId) {
		return airlineService.getAirlineInventoryList(airlineId);
	}

}
