package com.amila.ticket.test.objectsimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.amila.ticket.exceptions.TicketPlatformException;
import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Reservation;
import com.amila.ticket.objects.Trip;
import com.amila.ticket.objectsimpl.BusReservation;
import com.amila.ticket.objectsimpl.BusTrip;

class BusTripTest {

	Trip busTrip = null;
	Reservation busReservation = null;
	@BeforeEach
	void setUp() {
		busTrip = new BusTrip(Direction.A_TO_D);
		
		busReservation = new BusReservation();
		busReservation.setDate(LocalDate.now());
		busReservation.setStart(Location.A);
		busReservation.setDestination(Location.B);
		busReservation.setNoOfSeats(10);
	}

	@Test
	@DisplayName("Checking Reservation possibility for Trip")
	void isReservationPossibleTest() throws TicketPlatformException {
		assertEquals(true, busTrip.isReservationPossible(busReservation),
				"Reservation Should be posible at initial point for Trip");
	}
	
	@Test
	@DisplayName("Checking Reservation on Trip")
	void makeReservationTest1() throws TicketPlatformException {
		busReservation.setNoOfSeats(2);
		assertEquals("1A,2A", busTrip.makeReservation(busReservation),
				"Reservation Should be success for Trip initially");
	}
	
	@Test
	@DisplayName("Checking Reservation on Trip")
	void makeReservationTest2() throws TicketPlatformException {
		busReservation.setNoOfSeats(2);
		busTrip.makeReservation(busReservation);
		assertEquals("3A,4A", busTrip.makeReservation(busReservation),
				"Reservation Should be success for Trip initially");
	}
}
