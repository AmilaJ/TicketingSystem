package com.amila.ticket.test.objectsimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.amila.ticket.exceptions.TicketPlatformException;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Reservation;
import com.amila.ticket.objectsimpl.BusReservation;

class BusReservationTest {

	Reservation busReservation = null;

	@BeforeEach
	void setUp() {
		busReservation = new BusReservation();
		busReservation.setDate(LocalDate.now());
		busReservation.setStart(Location.A);
		busReservation.setDestination(Location.B);
		busReservation.setNoOfSeats(10);
	}

	@Test
	@DisplayName("Checking Reservation valid")
	void isReservationValidTest1() throws TicketPlatformException {

		assertEquals(true, busReservation.isReservationValid(),
				"Reservation Information Should be valid for valid information");
	}

	@Test
	@DisplayName("Checking Reservation validity with invalid start location")
	void isReservationValidTest2() throws TicketPlatformException {
		busReservation.setStart(null);

		Throwable exception = assertThrows(TicketPlatformException.class, () -> busReservation.isReservationValid());
		assertEquals("Reservation Start Location invalid", exception.getMessage(),
				"Invalid Reservation Information Should trigger exception with messege");

	}

	@Test
	@DisplayName("Checking Reservation validity with invalid destination locationd")
	void isReservationValidTest3() throws TicketPlatformException {
		busReservation.setDestination(null);

		Throwable exception = assertThrows(TicketPlatformException.class, () -> busReservation.isReservationValid());
		assertEquals("Reservation Destination Location invalid", exception.getMessage(),
				"Invalid Reservation Information Should trigger exception with messege");

	}
}
