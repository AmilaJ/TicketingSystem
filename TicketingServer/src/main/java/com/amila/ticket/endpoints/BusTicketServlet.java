package com.amila.ticket.endpoints;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amila.ticket.common.Common;
import com.amila.ticket.common.LocalDateDeserializer;
import com.amila.ticket.common.LocalDateSerializer;
import com.amila.ticket.controller.ReservationManager;
import com.amila.ticket.exceptions.TicketPlatformException;
import com.amila.ticket.objectsimpl.BusReservation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BusTicketServlet extends HttpServlet {

	private static final long serialVersionUID = -652576201762740481L;
	private final static Logger LOG = Logger.getLogger(BusTicketServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String jsonresponse = null;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
		Gson gson = gsonBuilder.setPrettyPrinting().create();
		try {

			String requestUrl = request.getRequestURI();
			if (requestUrl != null && !requestUrl.isEmpty()) {
				String msg = "Invoking URL\t:" + requestUrl;
				LOG.log(Level.INFO, msg);
				String context[] = requestUrl.split("/");
				if (context.length == 4 && context[3].equals("reservation")) {
					BusReservation reservationRequest = null;
					try {
						reservationRequest = gson.fromJson(request.getReader(), BusReservation.class);
					} catch (Exception e) {
						throw new TicketPlatformException("Error occured while trying to process the request, Please validate the request");
					}
					if (reservationRequest != null) {
						ReservationManager reservationManager = new ReservationManager();
						if (reservationRequest.isReservationValid()) {
							if (reservationManager.isReservationAvailabile(reservationRequest)) {
								BusReservation reservationResponse = reservationManager
										.getReservationInformation(reservationRequest);
								jsonresponse = gson.toJson(reservationResponse);
							} else {
								jsonresponse = gson.toJson(Common.generateError("Reservation Cannot Be Fulfilled",
										"Seats are Not available for the requested journey"));
							}
						} else {
							jsonresponse = gson.toJson(Common.generateError("Reservation Cannot Be Fulfilled",
									"Seats are Not available for the requested journey"));
						}
					} else {
						jsonresponse = gson
								.toJson(Common.generateError("Incorrect Request", "Please validate the request"));
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					}
				} else {
					jsonresponse = gson
							.toJson(Common.generateError("Incorrect Request", "Please validate the request path"));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			} else {
				jsonresponse = gson.toJson(Common.generateError("Incorrect Request", "Please validate the request path"));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} catch (TicketPlatformException e) {
			jsonresponse = gson.toJson(Common.generateError("Error Occured", e.getMessage()));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		response.getOutputStream().println(jsonresponse);
	}

	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String jsonresponse = null;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
		Gson gson = gsonBuilder.setPrettyPrinting().create();
		try {

			String requestUrl = request.getRequestURI();
			if (requestUrl != null && !requestUrl.isEmpty()) {
				String msg = "Invoking URL\t:" + requestUrl;
				LOG.log(Level.INFO, msg);
				String context[] = requestUrl.split("/");
				if (context.length == 4 && context[3].equals("reservation")) {
					BusReservation reservationRequest = null;
					try {
						reservationRequest = gson.fromJson(request.getReader(), BusReservation.class);
					} catch (Exception e) {
						throw new TicketPlatformException("Error occured while trying to process the request, Please validate the request");
					}
					if (reservationRequest != null) {
						ReservationManager reservationManager = new ReservationManager();
						if (reservationRequest.isReservationValid()) {
							if (reservationManager.isReservationAvailabile(reservationRequest)) {
								BusReservation reservationResponse = reservationManager
										.doReservation(reservationRequest);
								jsonresponse = gson.toJson(reservationResponse);
							} else {
								jsonresponse = gson.toJson(Common.generateError("Reservation Cannot Be Fulfilled",
										"Seats are Not available for the requested journey"));
							}
						} else {
							jsonresponse = gson.toJson(Common.generateError("Reservation Cannot Be Fulfilled",
									"Seats are Not available for the requested journey"));
						}
					} else {
						jsonresponse = gson
								.toJson(Common.generateError("Incorrect Request", "Please validate the request"));
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					}
				} else {
					jsonresponse = gson
							.toJson(Common.generateError("Incorrect Request", "Please validate the request path"));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			} else {
				jsonresponse = gson.toJson(Common.generateError("Incorrect Request", "Please validate the request path"));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} catch (TicketPlatformException e) {
			jsonresponse = gson.toJson(Common.generateError("Error Occured", e.getMessage()));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		response.getOutputStream().println(jsonresponse);
	}
}