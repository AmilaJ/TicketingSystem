package com.amila.ticket.objects;

import java.time.LocalDate;

import com.amila.ticket.exceptions.TicketPlatformException;

public interface TripInformation {

	Trip getTrip(LocalDate date, Direction direction) throws TicketPlatformException;

	void updateTrip(LocalDate date, Direction direction, Trip tripOnDay) throws TicketPlatformException;

	void addTrip(LocalDate date, Trip trip) throws TicketPlatformException;
}
