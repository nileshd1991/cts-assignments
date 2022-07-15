package com.flightapp.util.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flightapp.schema.repository.TicketBookingRepository;

@Component
public class PnrGenerationUtility {

	private static String PNR_PREFIX="PNR";
	
	@Autowired
	private TicketBookingRepository bookingRepository;
	
	public String generatePNRNumber() {
		boolean flag=true;
		String pnr=null;
		do {
			pnr=getRandomString();
			flag=bookingRepository.existsByPnr(pnr);
		} while (flag);
		return pnr;
	}

	private String getRandomString() {
	    boolean useLetters = true;
	    boolean useNumbers = false;
	    String generatedString = PNR_PREFIX+RandomStringUtils.random(5, useLetters, useNumbers).toUpperCase();
		return generatedString;
	}
}
