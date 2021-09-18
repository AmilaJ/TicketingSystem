package com.amila.ticket.objectsdata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Trip;

public class DataHolder {

	public static volatile Map<LocalDate, ArrayList<Trip>> trips = new TreeMap<>();

	public static Map<Location[], Integer> busticketPriceList = new HashMap<>();
	
	public static final Location[] Locationlist = Location.values();
	
	public static final int MaxSeatingCapacity=40;

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
	}
}
