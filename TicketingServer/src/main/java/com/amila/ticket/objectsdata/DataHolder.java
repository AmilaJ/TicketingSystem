package com.amila.ticket.objectsdata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Trip;

public class DataHolder {

	public static volatile Map<LocalDate, ArrayList<Trip>> trips = new TreeMap<>();

	public static Map<Location[], Integer> busticketPriceList = new HashMap<>();

	public static final Location[] Locationlist = Location.values();

	public static final int MAX_SEATING_CAPACITY = 40;

	public static List<String> seatingIds = null;
	
	static {

		Location[] ab = { Location.A, Location.B };
		Location[] ac = { Location.A, Location.C };
		Location[] ad = { Location.A, Location.D };
		Location[] bc = { Location.B, Location.C };
		Location[] bd = { Location.B, Location.D };
		Location[] cd = { Location.C, Location.D };

		busticketPriceList.put(ab, 50);
		busticketPriceList.put(ac, 100);
		busticketPriceList.put(ad, 150);
		busticketPriceList.put(bc, 50);
		busticketPriceList.put(bd, 100);
		busticketPriceList.put(cd, 50);

		seatingIds = new ArrayList<String>();
		char[] seatIdChars = { 'A', 'B', 'C', 'D' };
		for (char seatIdChar : seatIdChars) {
			for (int seatIdNumber = 1; seatIdNumber <= 10; seatIdNumber++) {
				seatingIds.add(String.valueOf(seatIdNumber) + String.valueOf(seatIdChar));
			}
		}

	}

	private DataHolder() {
	}
}
