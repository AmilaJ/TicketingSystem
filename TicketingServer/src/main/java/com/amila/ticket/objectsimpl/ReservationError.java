package com.amila.ticket.objectsimpl;

import com.amila.ticket.objects.PlatformError;
import com.google.gson.annotations.Expose;

public class ReservationError implements PlatformError {
	
	@Expose(serialize = true, deserialize = false)
	public String error;
	
	@Expose(serialize = true, deserialize = false)
	public String description;

}
