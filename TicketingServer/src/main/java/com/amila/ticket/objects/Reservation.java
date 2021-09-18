package com.amila.ticket.objects;

import java.time.LocalDate;

import com.amila.ticket.exceptions.TicketPlatformException;

public interface Reservation {
	
	void getJourneyPrice(int noOfSeats, Location start, Location destination);
	
	void reserveSeats(int noOfSeats, Location start, Location destination);

	void setPrice(String price);

	void setStatus(String status);

	LocalDate getDate();

	void setDate(LocalDate date);

	int getNoOfSeats();

	void setNoOfSeats(int noOfSeats);

	Location getStart();

	void setStart(Location start);

	Location getDestination();

	void setDestination(Location destination);

	boolean isReservationValid() throws TicketPlatformException;

	String getReaservationId();

	void setReaservationId(String reaservationId);

}
