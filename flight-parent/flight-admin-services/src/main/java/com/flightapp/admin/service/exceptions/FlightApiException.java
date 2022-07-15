package com.flightapp.admin.service.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class FlightApiException extends RuntimeException {
	
	private HttpStatus httpStatus;
	
	public FlightApiException(String message,HttpStatus status) {
		super(message);
		this.httpStatus=status;
	}
	
	public FlightApiException(Exception e,HttpStatus status) {
		super(e);
		this.httpStatus=status;
	}
	
	
	public FlightApiException(String message,Exception e,HttpStatus status) {
		super(message,e);
		this.httpStatus=status;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
