package com.flight.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "FlightEntity")
@Table(name = "flight_entity",schema = "flight")
public class FlightEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank
	private String flightName;
	@NotBlank
	private String airlineName;
	private Double price;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss",timezone = "Asia/Kolkata")
	private Timestamp startTime;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss",timezone = "Asia/Kolkata")
	private Timestamp returnTime;
	private String fromLocation;
	private String toLocation;
	private String wayType;

	public FlightEntity() {
		super();
	}

	public FlightEntity(Integer id, @NotBlank String flightName, @NotBlank String airlineName, Double price,
			Timestamp startTime, Timestamp returnTime, String fromLocation, String toLocation, String wayType) {
		super();
		this.id = id;
		this.flightName = flightName;
		this.airlineName = airlineName;
		this.price = price;
		this.startTime = startTime;
		this.returnTime = returnTime;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.wayType = wayType;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getWayType() {
		return wayType;
	}

	public void setWayType(String wayType) {
		this.wayType = wayType;
	}
	
}
