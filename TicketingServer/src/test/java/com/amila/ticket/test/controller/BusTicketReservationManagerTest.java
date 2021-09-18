package com.amila.ticket.test.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.amila.ticket.controller.BusTicketReservationManager;
import com.amila.ticket.exceptions.TicketPlatformException;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Reservation;
import com.amila.ticket.objectsimpl.BusReservation;
 
class BusTicketReservationManagerTest {
 
	BusTicketReservationManager  busTicketReservationManager=null;
	Reservation reservationRequest=null;
	@BeforeEach                                         
    void setUp() {
		busTicketReservationManager = new BusTicketReservationManager();
		reservationRequest=new BusReservation();
		reservationRequest.setDate(LocalDate.now());
		reservationRequest.setStart(Location.A);
		reservationRequest.setDestination(Location.B);
		reservationRequest.setNoOfSeats(10);
    }

	
    @Test                                               
    @DisplayName("Checking Reservation available")   
    void isReservationAvailabileTest() throws TicketPlatformException {
    	
        assertEquals(true, busTicketReservationManager.isReservationAvailabile(reservationRequest),     
                "Initial Reservation Check Should Always Success");  
    }
    

}