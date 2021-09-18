package com.amila.ticket.objectsimpl;

import com.amila.ticket.objects.PlatformError;
import com.google.gson.annotations.Expose;

public class ReservationError implements PlatformError {

	@Expose(serialize = true, deserialize = false)
	private String status;

	@Expose(serialize = true, deserialize = false)
	private String description;

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

}
