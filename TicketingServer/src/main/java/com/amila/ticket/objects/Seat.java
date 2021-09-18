package com.amila.ticket.objects;

public interface Seat {
	
	boolean checkAvailability(Location start, Location destination, Direction direction);

	String issueTicketForSeat(Ticket ticket);

	String getSeatId();

}
