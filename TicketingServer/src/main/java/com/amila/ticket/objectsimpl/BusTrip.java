package com.amila.ticket.objectsimpl;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.logging.Logger;

import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Reservation;
import com.amila.ticket.objects.Seat;
import com.amila.ticket.objects.Ticket;
import com.amila.ticket.objects.Trip;
import com.amila.ticket.objectsdata.DataHolder;

public class BusTrip implements Trip {
	
	private final static Logger LOG = Logger.getLogger(BusTrip.class.getName());
	
	private Direction direction = null;
	private ArrayList<Seat> busSeats = null;

	private BusTrip() {}

	public BusTrip(Direction direction) {
		this.direction = direction;
	}

	@Override
	public Direction getDirection() {
		return direction;
	}

	@Override
	public boolean isReservationPossible(Reservation reservation) {
		LOG.info("Checking Reservation for Trip");
		boolean available = false;
		if (busSeats == null) {
			available = true;
		} else {
			available = checkSeatsAvailable(reservation.getNoOfSeats(), reservation.getStart(),
					reservation.getDestination(), this.direction);
		}
		LOG.info("Reservation for Trip available:"+available);
		return available;
	}

	@Override
	synchronized public String makeReservation(Reservation reservation) {
		LOG.info("Make Reservation for Trip");
		StringJoiner seatsIdStringJoiner= new StringJoiner(",");
		int noOfSeatsToBeReserved = reservation.getNoOfSeats();
		Ticket ticket = new BusTicket(reservation.getReaservationId(), reservation.getStart(),reservation.getDestination());
		if (busSeats == null) {
			busSeats = new ArrayList<>();
		}
		for (Seat seat : busSeats) {
			if (seat.checkAvailability(reservation.getStart(), reservation.getDestination(), this.getDirection())) {
				seatsIdStringJoiner.add(seat.issueTicketForSeat(ticket));
				noOfSeatsToBeReserved--;
				if(noOfSeatsToBeReserved==0) {
					break;
				}
			}
		}
		while (noOfSeatsToBeReserved > 0) {
			Seat seat = new BusSeat(getNextSeatId());
			seatsIdStringJoiner.add(seat.issueTicketForSeat(ticket));
			busSeats.add(seat);
			noOfSeatsToBeReserved--;
		}
		LOG.info("Make Reservation for Trip completed");
		return seatsIdStringJoiner.toString();
	}

	private boolean checkSeatsAvailable(int noOfSeats, Location start, Location destination, Direction direction) {
		LOG.info("Checking Seats for Trip");
		boolean available = false;
		long count = busSeats.stream().filter(busSeat -> busSeat.checkAvailability(start, destination, direction))
				.count();
		if ((DataHolder.MAX_SEATING_CAPACITY - busSeats.size()) + count >= noOfSeats) {
			available = true;
		}
		LOG.info("Seats for Trip available"+available);
		return available;
	}
	
	private String getNextSeatId() {
		int seatnumber=0;
		if(this.busSeats!=null) {
			seatnumber=this.busSeats.size();
		}
		return DataHolder.seatingIds.get(seatnumber);
	}

}
