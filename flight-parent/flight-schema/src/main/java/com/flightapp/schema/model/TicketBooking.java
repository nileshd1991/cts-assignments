package com.flightapp.schema.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the ticket_booking database table.
 * 
 */
@Entity
@Table(name = "ticket_booking")
@NamedQuery(name = "TicketBooking.findAll", query = "SELECT t FROM TicketBooking t")
public class TicketBooking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private int ticketId;

	private Boolean cancelled;

	private String email;

	private String pnr;

	@Column(name = "seat_type")
	private String seatType;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "meal_type")
	private String mealType;

	@Column(name = "total_cost")
	private Double totalCost;

	// bi-directional many-to-one association to PassengerDetail
	@OneToMany(mappedBy = "ticketBooking")
	private List<PassengerDetail> passengerDetails;

	// bi-directional many-to-one association to Flight
	@ManyToOne
	@JoinColumn(name = "flight_id")
	private FlightEntity flight;

	public TicketBooking() {
	}

	public int getTicketId() {
		return this.ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public Boolean getCancelled() {
		return this.cancelled;
	}

	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPnr() {
		return this.pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getSeatType() {
		return this.seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<PassengerDetail> getPassengerDetails() {
		return this.passengerDetails;
	}

	public void setPassengerDetails(List<PassengerDetail> passengerDetails) {
		this.passengerDetails = passengerDetails;
	}

	public PassengerDetail addPassengerDetail(PassengerDetail passengerDetail) {
		getPassengerDetails().add(passengerDetail);
		passengerDetail.setTicketBooking(this);

		return passengerDetail;
	}

	public PassengerDetail removePassengerDetail(PassengerDetail passengerDetail) {
		getPassengerDetails().remove(passengerDetail);
		passengerDetail.setTicketBooking(null);

		return passengerDetail;
	}

	public FlightEntity getFlight() {
		return flight;
	}

	public void setFlight(FlightEntity flight) {
		this.flight = flight;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

}