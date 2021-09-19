package com.amila.ticket.test.common;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.amila.ticket.common.Common;
import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.PlatformError;

class CommonTest {

	@Test
	@DisplayName("Checking Common GetDirection A_TO_D")
	void getDirectionTest1() {

		assertEquals(Direction.A_TO_D, Common.getDirection(Location.A, Location.C),
				"Check The Direction Correctly retrieved for Locations A_TO_D");
	}

	@Test
	@DisplayName("Checking Common GetDirection D_TO_A")
	void getDirectionTest2() {

		assertEquals(Direction.D_TO_A, Common.getDirection(Location.B, Location.A),
				"Check The Direction Correctly retrieved for Locations D_TO_A");
	}
	
	
	@Test
	@DisplayName("Validate generated common Error Object")
	void generateErrorTest() {
		String error="Error";
		String errorDescription="Error Description";
		PlatformError platformError=Common.generateError(error,errorDescription);
		assertAll("Validate generated Error Object",
		        () -> assertEquals(error, platformError.getStatus()),
		        () -> assertEquals(errorDescription, platformError.getDescription())
		    );
	}
	
	@Test
	@DisplayName("Validate RandomFourDigits number generation")
	void getRandomFourDigitsTest() {
		String RandomNumber1=Common.getRandomFourDigits();
		String RandomNumber2=Common.getRandomFourDigits();
		assertAll("Validate generated Four Digits Number",
		        () -> assertNotEquals(RandomNumber1, RandomNumber2),
		        () -> assertEquals(4, RandomNumber1.length()),
		        () -> assertEquals(4,RandomNumber2.length())
		    );
	}
	
	@Test
	@DisplayName("Checking GSON Object Creation")
	void getgetGsonObjectTest() {
		
		assertNotEquals(null, Common.getGsonObject(),
				"Checking GSON Object Creation providing not null object");
	}
	
	@Test
	@DisplayName("Checking Departure Arrival String")
	void getDepartureArrivalTest() {

		assertEquals("Departure at 09:00 AM -- Arrival at 11:00 AM", Common.getDepartureArrival(Location.A, Location.C),
				"Check The Departure Arrival String for Locations A to C");
	}
}
