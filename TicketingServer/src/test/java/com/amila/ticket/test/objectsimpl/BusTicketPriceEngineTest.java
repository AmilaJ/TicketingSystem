package com.amila.ticket.test.objectsimpl;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Reservation;
import com.amila.ticket.objectsimpl.BusTicketPriceEngine;

class BusTicketPriceEngineTest {
	
	BusTicketPriceEngine  busTicketPriceEngine=null;
	Reservation reservationRequest=null;
	@BeforeEach                                         
    void setUp() {
		busTicketPriceEngine = new BusTicketPriceEngine();
    }
	
	@Test
	@DisplayName("Checking Price Engine Calculations")
	void getcalculatePriceTest1() {

		assertEquals("LKR500.00", busTicketPriceEngine.calculatePrice(5,Location.A, Location.C),
				"Checking Price Engine Calculations from A to C with 100 unit price");
	}
	
	@Test
	@DisplayName("Checking Common GetDirection")
	void getcalculatePriceTest(){

		assertEquals("LKR1,150.00", busTicketPriceEngine.calculatePrice(23,Location.B, Location.A),
				"Checking Price Engine Calculations from B to A with 50 unit price");
	}

}
