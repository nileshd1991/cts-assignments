package com.flightapp.schema.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the airline database table.
 * 
 */
@Entity(name = "AirlineEntity")
@Table(name="airline")
@NamedQuery(name="AirlineEntity.findAll", query="SELECT a FROM AirlineEntity a")
public class AirlineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="airline_id", unique=true, nullable=false)
	private Integer airlineId;

	private Boolean active;

	@Column(length=250)
	private String address;

	@Column(length=250)
	private String contact;

	@Column(length=250)
	private String name;

	//bi-directional many-to-one association to Flight
	@OneToMany(mappedBy="airline")
	private List<FlightEntity> flights;

	public AirlineEntity() {
	}

	public Integer getAirlineId() {
		return this.airlineId;
	}

	public void setAirlineId(Integer airlineId) {
		this.airlineId = airlineId;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FlightEntity> getFlights() {
		return this.flights;
	}

	public void setFlights(List<FlightEntity> flights) {
		this.flights = flights;
	}

	public FlightEntity addFlight(FlightEntity flight) {
		getFlights().add(flight);
		flight.setAirline(this);

		return flight;
	}

	public FlightEntity removeFlight(FlightEntity flight) {
		getFlights().remove(flight);
		flight.setAirline(null);

		return flight;
	}

}