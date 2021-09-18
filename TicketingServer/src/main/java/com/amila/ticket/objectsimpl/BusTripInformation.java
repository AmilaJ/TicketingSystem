package com.amila.ticket.objectsimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.amila.ticket.exceptions.TicketPlatformException;
import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Trip;
import com.amila.ticket.objects.TripInformation;
import com.amila.ticket.objectsdata.DataHolder;

public class BusTripInformation implements TripInformation {

	private final static Logger LOG = Logger.getLogger(BusTripInformation.class.getName());
	
	@Override
	public Trip getTrip(LocalDate date, Direction direction) throws TicketPlatformException {
		LOG.info("Requesting Trip information for Date Direction");
		Trip trip = null;
		try {
			ArrayList<Trip> tripsOnDay = DataHolder.trips.get(date);
			if (tripsOnDay != null
					&& tripsOnDay.stream().anyMatch(TripElement -> TripElement.getDirection().equals(direction))) {
				trip = tripsOnDay.stream().filter(TripElement -> TripElement.getDirection().equals(direction))
						.reduce((a, b) -> {
							throw new IllegalStateException("Multiple elements found");
						}).get();
			}
		} catch (IllegalStateException e) {
			throw new TicketPlatformException("Trip count exceeded incorrect information exists");
		}
		LOG.info("Responding Trip information for Date Direction");
		return trip;
	}

	@Override
	synchronized public void addTrip( LocalDate date, Trip trip) throws TicketPlatformException {
		LOG.info("Adding new Trip information Record");
		if (DataHolder.trips.containsKey(date)) {
			ArrayList<Trip> tripsOnDay = DataHolder.trips.get(date);
			if (tripsOnDay.stream().anyMatch(TripElement -> TripElement.getDirection().equals(trip.getDirection()))) {
				throw new TicketPlatformException("Trying to add invalid trip information for existing direction");
			} else {
				tripsOnDay.add(trip);
				DataHolder.trips.replace(date, tripsOnDay);
			}
		} else {
			ArrayList<Trip> tripsOnDay = new ArrayList<Trip>();
			tripsOnDay.add(trip);
			DataHolder.trips.put(date, tripsOnDay);
		}
		LOG.info("Adding new Trip information Record Completed");
	}

	@Override
	synchronized public void updateTrip(LocalDate date, Direction direction, Trip tripOnDay) throws TicketPlatformException {
		LOG.info("Updating new Trip information Record");
		ArrayList<Trip> tripsOnDay = DataHolder.trips.get(date);
		int index = 0;
		boolean exists = false;
		for (Trip trip : tripsOnDay) {
			if (trip.getDirection().equals(direction)) {
				exists = true;
				break;
			}
			index++;
		}
		if (exists) {
			tripsOnDay.set(index, tripOnDay);
		} else {
			throw new TicketPlatformException("Trying to update non existent Trip Information");
		}
		LOG.info("Updating new Trip information Record Completed");
	}

}
