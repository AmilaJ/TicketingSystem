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
import com.amila.ticket.objects.TripInformation;
import com.amila.ticket.objectsimpl.BusReservation;
import com.amila.ticket.objectsimpl.BusTrip;
import com.amila.ticket.objectsimpl.BusTripInformation;

public class BusTripInformationTest {

	TripInformation busTripInformation = null;
	
	Reservation busReservation = null;

	@BeforeEach
	void setUp() {
		busTripInformation = new BusTripInformation();

		busReservation = new BusReservation();
		busReservation.setDate(LocalDate.now());
		busReservation.setStart(Location.B);
		busReservation.setDestination(Location.A);
		busReservation.setNoOfSeats(10);
	}

	@Test
	@DisplayName("Checking getting trip information for a day and direction")
	void getTripTest() throws TicketPlatformException {
		assertEquals(null, busTripInformation.getTrip(LocalDate.now(), Direction.A_TO_D),
				"Trip Should be null at initial point");
	}

	@Test
	@DisplayName("Checking adding trip information for a day")
	void addTripTest() throws TicketPlatformException {
		LocalDate localdate = LocalDate.now();
		Trip busTrip = new BusTrip(Direction.A_TO_D);
		busTripInformation.addTrip(localdate, busTrip);
		assertEquals(busTrip, busTripInformation.getTrip(LocalDate.now(), Direction.A_TO_D),
				"Trip Should be added correctly and the same should be fetched correctly");
	}

	@Test
	@DisplayName("Checking adding trip information for a day")
	void updateTripTest() throws TicketPlatformException {
		LocalDate localdate = LocalDate.now();
		Trip busTrip = new BusTrip(Direction.D_TO_A);
		busTripInformation.addTrip(localdate, busTrip);
		busTrip.makeReservation(busReservation);
		busTripInformation.updateTrip(localdate, Direction.D_TO_A, busTrip);
		assertEquals(busTrip, busTripInformation.getTrip(LocalDate.now(), Direction.D_TO_A),
				"Trip Should be added correctly and the same should be fetched correctly");
	}

}
