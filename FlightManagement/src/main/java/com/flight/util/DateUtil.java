package com.flight.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	private static String DATE_TIME_FORMAT="DD/MM/YYYY HH:mm:ss";
	
	public LocalDateTime getLocalDateTime(String dateTimeStr){
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
		LocalDateTime dateTime=LocalDateTime.parse(dateTimeStr,formatter);
		return dateTime;
	}
	
	public Timestamp getTimestamp(LocalDateTime dateTime){
		return Timestamp.valueOf(dateTime);
	}
	
	public Timestamp getTimestampFromString(String dateTimeStr) {
		LocalDateTime localDateTime = getLocalDateTime(dateTimeStr);
		Timestamp timestamp = getTimestamp(localDateTime);
		return timestamp;
	}
}	
