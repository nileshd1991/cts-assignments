package com.flightapp.schema.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the passenger_details database table.
 * 
 */
@Entity
@Table(name="passenger_details")
@NamedQuery(name="PassengerDetail.findAll", query="SELECT p FROM PassengerDetail p")
public class PassengerDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="passenger_id")
	private int passengerId;

	private int age;

	private String gender;

	private String name;

	//bi-directional many-to-one association to TicketBooking
	@ManyToOne
	@JoinColumn(name="ticket_id")
	private TicketBooking ticketBooking;

	public PassengerDetail() {
	}

	public int getPassengerId() {
		return this.passengerId;
	}

	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TicketBooking getTicketBooking() {
		return this.ticketBooking;
	}

	public void setTicketBooking(TicketBooking ticketBooking) {
		this.ticketBooking = ticketBooking;
	}

}