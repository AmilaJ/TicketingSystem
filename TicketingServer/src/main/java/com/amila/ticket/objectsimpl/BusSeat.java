package com.amila.ticket.objectsimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Seat;
import com.amila.ticket.objects.Ticket;

public class BusSeat implements Seat {

	List<Ticket> tickets = null;
	private String seatId = null;

	private BusSeat() {
	}

	public BusSeat(String seatId) {
		this.seatId = seatId;
	}
	
	@Override
	public String getSeatId() {
		return this.seatId;
	}

	@Override
	public boolean checkAvailability(Location start, Location destination, Direction direction) {
		boolean available = false;
		if (tickets == null) {
			available = true;
		} else {
			if (tickets.stream().anyMatch(ticket -> !isSeatAvailable(ticket, start, destination, direction))) {
				available = false;
			} else {
				available = true;
			}
		}
		return available;
	}

	@Override
	synchronized public String issueTicketForSeat(Ticket ticket) {
		if (tickets == null) {
			tickets = new ArrayList<>();
		}
		tickets.add(ticket);
		return this.getSeatId();
	}

	private boolean isSeatAvailable(Ticket ticket, Location start, Location destination, Direction direction) {
		boolean available = false;
		ArrayList<Location> locationRange = null;
		if (Direction.A_TO_D.equals(direction)) {
			EnumSet<Location> locationRangeSet = EnumSet.range(start, destination);
			locationRange = new ArrayList<Location>(locationRangeSet);
		} else if (Direction.D_TO_A.equals(direction)) {
			EnumSet<Location> locationRangeSet = EnumSet.range(destination, start);
			locationRange = new ArrayList<Location>(locationRangeSet);
			Collections.reverse(locationRange);
		}
		if (locationRange != null) {
			if ((locationRange.contains(ticket.getStart())
					&& locationRange.indexOf(ticket.getStart()) != locationRange.size() - 1)
					|| (locationRange.contains(ticket.getDestination())
							&& locationRange.indexOf(ticket.getDestination()) != 0)) {
				available = false;
			} else {
				available = true;
			}
		}
		return available;
	}
}
