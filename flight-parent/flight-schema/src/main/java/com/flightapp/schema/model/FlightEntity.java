package com.flightapp.schema.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the flight database table.
 * 
 */
@Entity(name = "FlightEntity")
@Table(name = "flight")
@NamedQuery(name = "FlightEntity.findAll", query = "SELECT f FROM FlightEntity f")
public class FlightEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "flight_id", unique = true, nullable = false)
	private Integer flightId;

	@Column(name = "business_class_cost")
	private Double businessClassCost;

	@Column(name = "business_class_seats")
	private int businessClassSeats;

	@Column(name = "from_location", length = 250)
	private String fromLocation;

	@Column(length = 250)
	private String name;

	@Column(name = "non_business_class_cost")
	private Double nonBusinessClassCost;

	@Column(name = "non_business_class_seats")
	private Integer nonBusinessClassSeats;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "return_time")
	private Date returnTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	private Date startTime;

	@Column(name = "to_location", length = 250)
	private String toLocation;

	@Column(name = "way_type", length = 250)
	private String wayType;

	// bi-directional many-to-one association to Airline
	@ManyToOne
	@JoinColumn(name = "airline_id", nullable = false)
	private AirlineEntity airline;

	// bi-directional many-to-one association to TicketBooking
	@OneToMany(mappedBy = "flight")
	private List<TicketBooking> ticketBookings;

	public FlightEntity() {
	}

	public Integer getFlightId() {
		return this.flightId;
	}

	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}

	public Double getBusinessClassCost() {
		return this.businessClassCost;
	}

	public void setBusinessClassCost(Double businessClassCost) {
		this.businessClassCost = businessClassCost;
	}

	public Integer getBusinessClassSeats() {
		return this.businessClassSeats;
	}

	public void setBusinessClassSeats(Integer businessClassSeats) {
		this.businessClassSeats = businessClassSeats;
	}

	public String getFromLocation() {
		return this.fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNonBusinessClassCost() {
		return this.nonBusinessClassCost;
	}

	public void setNonBusinessClassCost(Double nonBusinessClassCost) {
		this.nonBusinessClassCost = nonBusinessClassCost;
	}

	public Integer getNonBusinessClassSeats() {
		return this.nonBusinessClassSeats;
	}

	public void setNonBusinessClassSeats(Integer nonBusinessClassSeats) {
		this.nonBusinessClassSeats = nonBusinessClassSeats;
	}

	public Date getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getToLocation() {
		return this.toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public String getWayType() {
		return this.wayType;
	}

	public void setWayType(String wayType) {
		this.wayType = wayType;
	}

	public AirlineEntity getAirline() {
		return this.airline;
	}

	public void setAirline(AirlineEntity airline) {
		this.airline = airline;
	}

	public List<TicketBooking> getTicketBookings() {
		return ticketBookings;
	}

	public void setTicketBookings(List<TicketBooking> ticketBookings) {
		this.ticketBookings = ticketBookings;
	}

	public void setBusinessClassSeats(int businessClassSeats) {
		this.businessClassSeats = businessClassSeats;
	}

	public TicketBooking addTicketBooking(TicketBooking ticketBooking) {
		getTicketBookings().add(ticketBooking);
		ticketBooking.setFlight(this);

		return ticketBooking;
	}

	public TicketBooking removeTicketBooking(TicketBooking ticketBooking) {
		getTicketBookings().remove(ticketBooking);
		ticketBooking.setFlight(null);

		return ticketBooking;
	}

}