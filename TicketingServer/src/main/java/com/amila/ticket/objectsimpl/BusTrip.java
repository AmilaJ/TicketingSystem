package com.amila.ticket.objectsimpl;

import java.util.ArrayList;

import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Reservation;
import com.amila.ticket.objects.Seat;
import com.amila.ticket.objects.Ticket;
import com.amila.ticket.objects.Trip;
import com.amila.ticket.objectsdata.DataHolder;

public class BusTrip implements Trip {
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
		boolean available = false;
		if (busSeats == null) {
			available = true;
		} else {
			available = checkSeatsAvailable(reservation.getNoOfSeats(), reservation.getStart(),
					reservation.getDestination(), this.direction);
		}
		return available;
	}

	@Override
	public void makeReservation(Reservation reservation) {
		int noOfSeatsToBeReserved = reservation.getNoOfSeats();
		Ticket ticket = new BusTicket(reservation.getReaservationId(), reservation.getStart(),reservation.getDestination());
		if (busSeats == null) {
			busSeats = new ArrayList<>();
		}
		for (Seat seat : busSeats) {
			if (seat.checkAvailability(reservation.getStart(), reservation.getDestination(), this.getDirection())) {

				seat.issueTicketForSeat(ticket);
				noOfSeatsToBeReserved--;
			}
		}
		while (noOfSeatsToBeReserved > 0) {
			Seat seat = new BusSeat();
			seat.issueTicketForSeat(ticket);
			busSeats.add(seat);
			noOfSeatsToBeReserved--;
		}
	}

	private boolean checkSeatsAvailable(int noOfSeats, Location start, Location destination, Direction direction) {
		boolean available = false;
		long count = busSeats.stream().filter(busSeat -> busSeat.checkAvailability(start, destination, direction))
				.count();
		if ((DataHolder.MaxSeatingCapacity - busSeats.size()) + count > noOfSeats) {
			available = true;
		}
		return available;
	}

}
