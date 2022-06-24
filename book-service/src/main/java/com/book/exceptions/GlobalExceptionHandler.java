package com.book.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BookApiException.class)
	ResponseEntity<ApiError> handleException(BookApiException e){
		return new ResponseEntity<ApiError>(new ApiError(e.getHttpStatus(), e.getMessage(), e), e.getHttpStatus());
	}
	
	@ExceptionHandler(Exception.class)
	ResponseEntity<String> handleExceptionForGenericException(Exception e){
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
