package com.amila.ticket.objectsimpl;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.amila.ticket.objects.Location;
import com.amila.ticket.objects.PriceEngine;
import com.amila.ticket.objectsdata.DataHolder;

public class BusTicketPriceEngine implements PriceEngine {
	private final static Logger LOG = Logger.getLogger(BusTicketPriceEngine.class.getName());

	int unitprice;

	public String calculatePrice(int noOfSeats, Location start, Location destination) {
		unitprice = 0;
		DataHolder.busticketPriceList.forEach((locations, price) -> {
			if (locations != null && locations.length == 2
					&& ((locations[0].equals(start) && locations[1].equals(destination))
							|| (locations[0].equals(destination) && locations[1].equals(start)))) {
				unitprice = price;

			}
		});
		int totalPrice = unitprice * noOfSeats;
		LOG.log(Level.INFO, "TotalPrice:"+totalPrice);
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "LK"));
		String priceString = formatter.format(totalPrice);
		return priceString;
	}

}
