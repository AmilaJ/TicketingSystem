package com.amila.ticket.objectsimpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.amila.ticket.exceptions.TicketPlatformException;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Reservation;
import com.amila.ticket.objectsdata.DataHolder;
import com.google.gson.annotations.Expose;

public class BusReservation implements Reservation {

	@Expose(serialize = true, deserialize = true)
	private LocalDate date;

	@Expose(serialize = true, deserialize = true)
	private int noOfSeats;

	@Expose(serialize = true, deserialize = true)
	private Location start;

	@Expose(serialize = true, deserialize = true)
	private Location destination;

	@Expose(serialize = true, deserialize = false)
	public String price;

	@Expose(serialize = true, deserialize = false)
	public String status;
	
	@Expose(serialize = true, deserialize = false)
	private String reaservationId;

	@Override
	public void getJourneyPrice(int noOfSeats, Location start, Location destination) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reserveSeats(int noOfSeats, Location start, Location destination) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int getNoOfSeats() {
		return noOfSeats;
	}

	@Override
	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	@Override
	public Location getStart() {
		return start;
	}

	@Override
	public void setStart(Location start) {
		this.start = start;
	}

	@Override
	public Location getDestination() {
		return destination;
	}

	@Override
	public void setDestination(Location destination) {
		this.destination = destination;
	}
	
	@Override
	public String getReaservationId() {
		return reaservationId;
	}

	@Override
	public void setReaservationId(String reaservationId) {
		this.reaservationId = reaservationId;
	}
	
	
	@Override
	public boolean isReservationValid() throws TicketPlatformException {
		boolean valid = false;
		if (this.date != null && this.date.isAfter(LocalDate.now().minusDays(1))) {
			if (this.noOfSeats > 0 && this.noOfSeats <= DataHolder.MaxSeatingCapacity) {
				List<Location> Locations = Arrays.asList(Location.values());
				if (this.start != null && Locations.contains(this.start)) {
					if (this.destination != null && Locations.contains(this.destination)) {
						if (!this.start.equals(this.destination)) {
							valid = true;
						} else {
							throw new TicketPlatformException("Reservation Start and Destination Locations are same ");
						}
					} else {
						throw new TicketPlatformException("Reservation Destination Location invalid");
					}
				} else {
					throw new TicketPlatformException("Reservation Start Location invalid");
				}
			} else {
				throw new TicketPlatformException("Reservation No of Seats invalid, Value should be between 1 - 40");
			}
		} else {
			throw new TicketPlatformException("Reservation Date invalid, Please provide valid date");
		}
		return valid;
	}



}
