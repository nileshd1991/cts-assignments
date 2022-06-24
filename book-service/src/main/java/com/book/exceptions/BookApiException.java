package com.book.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class BookApiException extends Exception {
	
	private HttpStatus httpStatus;
	
	public BookApiException(String message,HttpStatus status) {
		super(message);
		this.httpStatus=status;
	}
	
	public BookApiException(Exception e,HttpStatus status) {
		super(e);
		this.httpStatus=status;
	}
	
	
	public BookApiException(String message,Exception e,HttpStatus status) {
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
