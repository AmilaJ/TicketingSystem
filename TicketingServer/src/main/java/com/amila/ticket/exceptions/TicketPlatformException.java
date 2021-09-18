package com.amila.ticket.exceptions;

public class TicketPlatformException extends Exception {

	private static final long serialVersionUID = -5241568020792143216L;

	public TicketPlatformException(String errorMessage) {
		super(errorMessage);
	}

}
