package com.amila.ticket.objects;

import java.time.LocalDate;

import com.amila.ticket.exceptions.TicketPlatformException;

public interface TripInformation {

	Trip getTrip(LocalDate date, Direction direction) throws TicketPlatformException;

	void addTrip(Trip trip, LocalDate date) throws TicketPlatformException;
}
