package com.amila.ticket.common;

import java.time.LocalDate;
import java.util.Random;

import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.PlatformError;
import com.amila.ticket.objectsdata.DataHolder;
import com.amila.ticket.objectsimpl.ReservationError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Common {
	
	//Error Messages
	public static final String RESERVATION_CANNOT_BE_FULFILLED="Reservation Cannot Be Fulfilled";
	public static final String SEATS_ARE_NOT_AVAILABLE="Seats are Not available for the requested journey";
	public static final String INCORRECT_REQUEST="Incorrect Request";
	public static final String PLEASE_VALIDATE_REQUEST="Please validate the request";
	public static final String PLEASE_VALIDATE_REQUEST_PATH="Please validate the request path";
	public static final String ERROR_OCCURED_WHILE_PROCESSING="Error Occured while processing";
	public static final String ERROR_OCCURED_WHILE_PROCESSING_REQUEST="Error Occured while processing Request, Please validate Request";
	
	public static final String CONTEXT_PATH="reservation";
	
	public static PlatformError generateError(String error, String description) {
		PlatformError reservationError = new ReservationError();
		reservationError.setStatus(error);
		reservationError.setDescription(description);
		return reservationError;

	}

	public static Direction getDirection(Location start, Location destination) {
		Direction direction = null;
		for (Location location : DataHolder.Locationlist) {
			if (start.equals(location)) {
				direction = Direction.A_TO_D;
				break;
			} else if (destination.equals(location)) {
				direction = Direction.D_TO_A;
				break;
			}
		}
		return direction;
	}

	private Common() {
	}

	public static String getRandomFourDigits() {
		return String.format("%04d", new Random().nextInt(10000));
	}
	
	public static Gson getGsonObject() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
		return gsonBuilder.setPrettyPrinting().create();
	}
}
