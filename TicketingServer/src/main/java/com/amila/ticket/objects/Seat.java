package com.amila.ticket.objects;

public interface Seat {
	
	boolean checkAvailability(Location start, Location destination, Direction direction);

	void issueTicketForSeat(Ticket ticket);

	String getSeatId();

	void setSeatId(String seatId);

}
