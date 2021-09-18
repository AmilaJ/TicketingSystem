package com.amila.ticket.client.objects;

import java.time.LocalDate;

import com.google.gson.annotations.Expose;

public class BusReservation {

	@Expose(serialize = true, deserialize = true)
	private LocalDate date;

	@Expose(serialize = true, deserialize = true)
	private int noOfSeats;

	@Expose(serialize = true, deserialize = true)
	private String start;

	@Expose(serialize = true, deserialize = true)
	private String destination;



	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	


}
