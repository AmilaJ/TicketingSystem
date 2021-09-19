package com.amila.ticket.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.amila.ticket.common.Common;
import com.amila.ticket.exceptions.TicketPlatformException;
import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Reservation;
import com.amila.ticket.objects.ReservationManager;
import com.amila.ticket.objects.Trip;
import com.amila.ticket.objectsimpl.BusReservation;
import com.amila.ticket.objectsimpl.BusTicketPriceEngine;
import com.amila.ticket.objectsimpl.BusTrip;
import com.amila.ticket.objectsimpl.BusTripInformation;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class BusTicketReservationManager implements ReservationManager {

	private final static Logger LOG = Logger.getLogger(BusTicketReservationManager.class.getName());

	@Override
	public Reservation validateAndFetchDataFromIncomingRequest(HttpServletRequest request)
			throws TicketPlatformException, JsonSyntaxException, JsonIOException, IOException {
		Reservation reservation = null;
		String requestUrl = request.getRequestURI();
		if (requestUrl != null && !requestUrl.isEmpty()) {
			LOG.info(requestUrl+" Triggered");
			String context[] = requestUrl.split("/");
			if (context.length == 4 && context[3].equals(Common.CONTEXT_PATH)) {
				try {
					reservation = Common.getGsonObject().fromJson(request.getReader(), BusReservation.class);
				} catch (Exception e) {
					LOG.severe(e.getMessage());
					throw new TicketPlatformException(Common.PLEASE_VALIDATE_REQUEST);
				}
				if (reservation != null) {
					if (reservation.isReservationValid()) {
						return reservation;
					} else {
						throw new TicketPlatformException(Common.PLEASE_VALIDATE_REQUEST);
					}
				} else {
					throw new TicketPlatformException(Common.PLEASE_VALIDATE_REQUEST);
				}
			} else {
				throw new TicketPlatformException(Common.PLEASE_VALIDATE_REQUEST_PATH);
			}
		} else {
			throw new TicketPlatformException(Common.PLEASE_VALIDATE_REQUEST_PATH);
		}
	}

	@Override
	public boolean isReservationAvailabile(Reservation reservationRequest) throws TicketPlatformException {
		LOG.info("Checking Reservation Possible");
		boolean available = false;
		Direction direction = Common.getDirection(reservationRequest.getStart(), reservationRequest.getDestination());
		if (direction != null) {
			Trip tripOnDay = new BusTripInformation().getTrip(reservationRequest.getDate(), direction);
			if (tripOnDay != null) {
				if (tripOnDay.isReservationPossible(reservationRequest)) {
					available = true;
				}
			} else {
				available = true;
			}
		} else {
			throw new TicketPlatformException("Invalid Location information on Request");
		}
		LOG.info("Reservation Possiblilty:" + available);
		return available;
	}

	@Override
	public String getReservationInformation(HttpServletRequest request)
			throws TicketPlatformException, JsonSyntaxException, JsonIOException, IOException {
		LOG.info("Collecting Reservation Information");
		String jsonresponse = null;
		Gson gson = Common.getGsonObject();
		Reservation reservation = validateAndFetchDataFromIncomingRequest(request);
		if (this.isReservationAvailabile(reservation)) {
			String totalPrice = new BusTicketPriceEngine().calculatePrice(reservation.getNoOfSeats(),
					reservation.getStart(), reservation.getDestination());
			reservation.setPrice(totalPrice);
			reservation.setDuration(Common.getDepartureArrival(reservation.getStart(), reservation.getDestination()));
			reservation.setStatus("Seats are available for Reservation");
			jsonresponse = gson.toJson(reservation);
		} else {
			
			jsonresponse = gson.toJson(
					Common.generateError(Common.RESERVATION_CANNOT_BE_FULFILLED, Common.SEATS_ARE_NOT_AVAILABLE));
		}
		LOG.info("Returing Reservation Information");
		return jsonresponse;
	}

	@Override
	public String doReservation(HttpServletRequest request)
			throws TicketPlatformException, JsonSyntaxException, JsonIOException, IOException {
		LOG.info("Reservation Execution Started");
		String jsonresponse = null;
		Gson gson = Common.getGsonObject();
		Reservation reservation = validateAndFetchDataFromIncomingRequest(request);
		if (this.isReservationAvailabile(reservation)) {
			Direction direction = Common.getDirection(reservation.getStart(), reservation.getDestination());
			BusTripInformation busTripInformation = new BusTripInformation();
			reservation.setReaservationId(generateReservationId(reservation));
			Trip tripOnDay = busTripInformation.getTrip(reservation.getDate(), direction);
			if (tripOnDay == null) {
				tripOnDay = new BusTrip(direction);
				busTripInformation.addTrip(reservation.getDate(),tripOnDay);
			}
			String reservedSeats=tripOnDay.makeReservation(reservation);
			busTripInformation.updateTrip(reservation.getDate(), direction, tripOnDay);
			String totalPrice = new BusTicketPriceEngine().calculatePrice(reservation.getNoOfSeats(),
					reservation.getStart(), reservation.getDestination());
			reservation.setPrice(totalPrice);
			reservation.setDuration(Common.getDepartureArrival(reservation.getStart(), reservation.getDestination()));
			reservation.setStatus("Seats "+reservedSeats+" are allocated for Reservation");
			jsonresponse = gson.toJson(reservation);
		} else {
			jsonresponse = gson.toJson(
					Common.generateError(Common.RESERVATION_CANNOT_BE_FULFILLED, Common.SEATS_ARE_NOT_AVAILABLE));
		}
		LOG.info("Reservation Execution Completed");
		return jsonresponse;
	}

	private String generateReservationId(Reservation reservation) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(reservation.getDate().toEpochDay());
		stringBuilder.append(reservation.getStart().toString());
		stringBuilder.append(reservation.getDestination().toString());
		stringBuilder.append(Common.getRandomFourDigits());
		return stringBuilder.toString();
	}
	

}
