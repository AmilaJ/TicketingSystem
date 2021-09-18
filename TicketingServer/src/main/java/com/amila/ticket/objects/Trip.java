package com.amila.ticket.objects;

public interface Trip {

	boolean isReservationPossible(Reservation reservation);

	String makeReservation(Reservation reservation);

	Direction getDirection();

}
