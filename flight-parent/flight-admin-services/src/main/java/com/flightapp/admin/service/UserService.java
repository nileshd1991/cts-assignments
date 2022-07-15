package com.flightapp.admin.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.models.BookFlightTicket;
import com.flightapp.models.SearchFlight;
import com.flightapp.models.SearchFlightResponse;

@Service
public interface UserService {

	ResponseEntity<List<SearchFlightResponse>> searchFlight(SearchFlight body);

	ResponseEntity<BookFlightTicket> bookTicket(Integer flightId, @Valid BookFlightTicket bookFlightTicket);

	ResponseEntity<BookFlightTicket> getTicketDetails(String pnr);

	ResponseEntity<List<BookFlightTicket>> getTicketBookingHistory(String emailId);

	ResponseEntity<Void> cancelFlightTicket(String pnr);

}
