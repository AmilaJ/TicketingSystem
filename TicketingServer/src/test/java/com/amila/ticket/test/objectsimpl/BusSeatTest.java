package com.amila.ticket.test.objectsimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.amila.ticket.exceptions.TicketPlatformException;
import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Ticket;
import com.amila.ticket.objectsimpl.BusSeat;
import com.amila.ticket.objectsimpl.BusTicket;

class BusSeatTest {
	
	BusSeat busSeat = null;
	Ticket ticket =null;
	@BeforeEach
	void setUp() {
		busSeat = new BusSeat("3B");
		ticket = new BusTicket("18896AC9476",Location.A, Location.C);
	}
	
	@Test
	@DisplayName("Checking Seat availability")
	void checkAvailabilityTest1() throws TicketPlatformException {

		assertEquals(true, busSeat.checkAvailability(Location.A, Location.B,Direction.A_TO_D),
				"Seat should be available initially");
	}
	
	@Test
	@DisplayName("Checking Seat availability when already ticket issued")
	void checkAvailabilityTest2() throws TicketPlatformException {
		busSeat.issueTicketForSeat(ticket);
		assertEquals(false, busSeat.checkAvailability(Location.A, Location.C,Direction.A_TO_D),
				"Seat should not be available once ticket issued");
	}
	
	@Test
	@DisplayName("Checking Ticket Issuing for the Seat")
	void issueTicketForSeatTest() throws TicketPlatformException {

		assertEquals("3B", busSeat.issueTicketForSeat(ticket),
				"Ticket should be issued correctly");
	}

	

}
