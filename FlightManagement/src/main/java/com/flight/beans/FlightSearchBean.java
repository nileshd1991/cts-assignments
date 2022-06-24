package com.flight.beans;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FlightSearchBean {

	private String wayType;
	private String fromLocation;
	private String toLocation;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "Asia/Kolkata")
	private Timestamp startTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "Asia/Kolkata")
	private Timestamp returnTime;

	public FlightSearchBean() {
		// TODO Auto-generated constructor stub
	}

	public FlightSearchBean(String wayType, String fromLocation, String toLocation, Timestamp startTime,
			Timestamp returnTime) {
		super();
		this.wayType = wayType;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.startTime = startTime;
		this.returnTime = returnTime;
	}

	public String getWayType() {
		return wayType;
	}

	public void setWayType(String wayType) {
		this.wayType = wayType;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Timestamp returnTime) {
		this.returnTime = returnTime;
	}

}
