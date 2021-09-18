package com.amila.ticket.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.amila.ticket.common.Common;
import com.amila.ticket.exceptions.TicketPlatformException;
import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Trip;
import com.amila.ticket.objectsdata.DataHolder;
import com.amila.ticket.objectsimpl.BusReservation;
import com.amila.ticket.objectsimpl.BusTicketPriceEngine;
import com.amila.ticket.objectsimpl.BusTrip;
import com.amila.ticket.objectsimpl.BusTripInformation;

public class ReservationManager {

	public boolean isReservationAvailabile(BusReservation reservationRequest) throws TicketPlatformException {
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
		return available;
	}

	public BusReservation getReservationInformation(BusReservation reservation) throws TicketPlatformException {
		if (this.isReservationAvailabile(reservation)) {
			String totalPrice = new BusTicketPriceEngine().calculatePrice(reservation.getNoOfSeats(),
					reservation.getStart(), reservation.getDestination());
			reservation.setPrice(totalPrice);
			reservation.setStatus("Seats are available for Reservation");
		} else {
			throw new TicketPlatformException("Reservation cannot be fullfilled");
		}
		return reservation;
	}

	public BusReservation doReservation(BusReservation reservation) throws TicketPlatformException {
		if (this.isReservationAvailabile(reservation)) {
			Direction direction = Common.getDirection(reservation.getStart(), reservation.getDestination());
			BusTripInformation busTripInformation = new BusTripInformation();
			reservation.setReaservationId(generateReservationId(reservation));
			Trip tripOnDay = busTripInformation.getTrip(reservation.getDate(), direction);
			if (tripOnDay == null) {
				tripOnDay = new BusTrip(direction);
				busTripInformation.addTrip(tripOnDay, reservation.getDate());
			}
			tripOnDay.makeReservation(reservation);
			busTripInformation.updateTrip(reservation.getDate(), direction, tripOnDay);
			String totalPrice = new BusTicketPriceEngine().calculatePrice(reservation.getNoOfSeats(),
					reservation.getStart(), reservation.getDestination());
			reservation.setPrice(totalPrice);
			reservation.setStatus("Seats are allocated for Reservation");
		} else {
			throw new TicketPlatformException("Reservation cannot be fullfilled");
		}
		return reservation;
	}

	private String generateReservationId(BusReservation reservation) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(reservation.getDate().toEpochDay());
		stringBuilder.append(reservation.getStart().toString());
		stringBuilder.append(reservation.getDestination().toString());
		stringBuilder.append(Common.getRandomFourDigits());
		return stringBuilder.toString();
	}

}
