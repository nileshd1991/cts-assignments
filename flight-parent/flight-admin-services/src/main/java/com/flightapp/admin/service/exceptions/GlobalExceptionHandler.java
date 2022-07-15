package com.flightapp.admin.service.exceptions;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.flightapp.models.ApiError;

import feign.FeignException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(FlightApiException.class)
	ResponseEntity<ApiError> handleException(FlightApiException e){
		ApiError apiError=new ApiError();
		apiError.setMessage(e.getMessage());
		apiError.setStatusCode(e.getHttpStatus().value());
		apiError.setTimestamp(new Date());
		return new ResponseEntity<ApiError>(apiError, e.getHttpStatus());
	}
	
	@ExceptionHandler(FeignException.class)
	Map<String, Object> handleException(FeignException e, HttpServletResponse response){
		response.setStatus(e.status());
		return new JSONObject(e.contentUTF8()).toMap();
	}
}
