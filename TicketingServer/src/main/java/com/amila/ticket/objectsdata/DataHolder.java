package com.amila.ticket.objectsdata;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.amila.ticket.objects.Direction;
import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.Trip;

public class DataHolder {

	public static volatile Map<LocalDate, ArrayList<Trip>> trips = new TreeMap<>();

	public static Map<Location[], Integer> busticketPriceList = new HashMap<>();
	
	public static Map<Direction, Map<Location, LocalTime>> locationTimeList = new HashMap<>();

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
		
		Map<Location, LocalTime> A_TO_D_map=new HashMap<Location, LocalTime>();
		A_TO_D_map.put(Location.A, LocalTime.parse("09:00"));
		A_TO_D_map.put(Location.B, LocalTime.parse("10:00"));
		A_TO_D_map.put(Location.C, LocalTime.parse("11:00"));
		A_TO_D_map.put(Location.D, LocalTime.parse("12:00"));
		Map<Location, LocalTime> D_TO_A_map=new HashMap<Location, LocalTime>();
		D_TO_A_map.put(Location.A, LocalTime.parse("16:00"));
		D_TO_A_map.put(Location.B, LocalTime.parse("15:00"));
		D_TO_A_map.put(Location.C, LocalTime.parse("14:00"));
		D_TO_A_map.put(Location.D, LocalTime.parse("13:00"));
		locationTimeList.put(Direction.A_TO_D, A_TO_D_map);
		locationTimeList.put(Direction.D_TO_A, D_TO_A_map);
	
	}

	private DataHolder() {
	}
}
