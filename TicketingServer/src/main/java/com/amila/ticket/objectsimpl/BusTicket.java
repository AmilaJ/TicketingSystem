package com.amila.ticket.objectsimpl;

import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Ticket;

public class BusTicket implements Ticket {

	private String reservationId;
	private Location start;
	private Location destination;

	public BusTicket(String reservationId,Location start, Location destination) {
		this.reservationId = reservationId;
		this.start = start;
		this.destination = destination;
	}
	
	private BusTicket() {
	}

	@Override
	public String getReservationId() {
		return this.reservationId;
	}

	@Override
	public Location getStart() {
		return this.start;
	}

	@Override
	public Location getDestination() {
		return this.destination;
	}

}
