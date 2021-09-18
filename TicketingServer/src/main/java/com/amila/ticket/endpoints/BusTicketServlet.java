package com.amila.ticket.endpoints;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amila.ticket.common.Common;
import com.amila.ticket.controller.BusTicketReservationManager;
import com.amila.ticket.exceptions.TicketPlatformException;
import com.amila.ticket.objects.ReservationManager;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class BusTicketServlet extends HttpServlet {

	private static final long serialVersionUID = -652576201762740481L;
	private final static Logger LOG = Logger.getLogger(BusTicketServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.info("Inside doPost");
		String jsonresponse = null;
		Gson gson = Common.getGsonObject();
		try {
			ReservationManager reservationManager = new BusTicketReservationManager();
			jsonresponse = reservationManager.getReservationInformation(request);
		} catch (JsonIOException e) {
			LOG.severe(e.getMessage());
			jsonresponse = gson.toJson(Common.generateError(Common.ERROR_OCCURED_WHILE_PROCESSING_REQUEST,
					Common.PLEASE_VALIDATE_REQUEST));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (JsonSyntaxException e) {
			LOG.severe(e.getMessage());
			jsonresponse = gson.toJson(Common.generateError(Common.ERROR_OCCURED_WHILE_PROCESSING_REQUEST,
					Common.PLEASE_VALIDATE_REQUEST));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (TicketPlatformException e) {
			jsonresponse = gson.toJson(Common.generateError(Common.ERROR_OCCURED_WHILE_PROCESSING, e.getMessage()));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		response.getOutputStream().println(jsonresponse);
	}
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.info("Inside doPut");
		String jsonresponse = null;
		Gson gson = Common.getGsonObject();
		try {
			ReservationManager reservationManager = new BusTicketReservationManager();
			jsonresponse = reservationManager.doReservation(request);
		} catch (JsonIOException e) {
			LOG.severe(e.getMessage());
			jsonresponse = gson.toJson(Common.generateError(Common.ERROR_OCCURED_WHILE_PROCESSING_REQUEST,
					Common.PLEASE_VALIDATE_REQUEST));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (JsonSyntaxException e) {
			LOG.severe(e.getMessage());
			jsonresponse = gson.toJson(Common.generateError(Common.ERROR_OCCURED_WHILE_PROCESSING_REQUEST,
					Common.PLEASE_VALIDATE_REQUEST));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (TicketPlatformException e) {
			jsonresponse = gson.toJson(Common.generateError(Common.ERROR_OCCURED_WHILE_PROCESSING, e.getMessage()));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		response.getOutputStream().println(jsonresponse);
	}

}