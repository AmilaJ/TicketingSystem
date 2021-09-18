package com.amila.ticket.objects;

public interface Trip {

	boolean isReservationPossible(Reservation reservation);

	void makeReservation(Reservation reservation);

	Direction getDirection();

}
