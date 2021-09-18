package com.amila.ticket.objects;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.amila.ticket.exceptions.TicketPlatformException;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public interface ReservationManager {

	boolean isReservationAvailabile(Reservation reservationRequest) throws TicketPlatformException;

	String getReservationInformation(HttpServletRequest request) throws TicketPlatformException, JsonSyntaxException, JsonIOException, IOException;

	String doReservation(HttpServletRequest request) throws TicketPlatformException, JsonSyntaxException, JsonIOException, IOException;

	Reservation validateAndFetchDataFromIncomingRequest(HttpServletRequest request)
			throws TicketPlatformException, JsonSyntaxException, JsonIOException, IOException;

}
