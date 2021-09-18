package com.amila.ticket.common;

import java.util.Random;

import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.PlatformError;
import com.amila.ticket.objectsdata.DataHolder;
import com.amila.ticket.objectsimpl.ReservationError;

public class Common {

	public static PlatformError generateError(String error, String description) {
		ReservationError reservationError = new ReservationError();
		reservationError.error = error;
		reservationError.description = description;
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
}
