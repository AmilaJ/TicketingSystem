package com.amila.ticket.client.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.amila.ticket.client.common.Common;
import com.amila.ticket.client.objects.BusReservation;

public class RestClient {

	public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {
		disableSSLVerification();
		Scanner in = new Scanner(System.in);
		try {
			System.out.print("Enter Rest Endpoint URL:");
			String URLInput = in.next();
			boolean exit = false;
			while (!exit) {
				System.out.print("Enter option number (1).Check Reservation (2).Make Reservation:");

				int option = in.nextInt();

				System.out.print("Enter date in format yyyy-mm-dd(2007-10-21):");

				String dateInput = in.next();
				LocalDate date = LocalDate.parse(dateInput);

				System.out.print("Enter Start Location:");

				String start = in.next();

				System.out.print("Enter Destination Location:");

				String destination = in.next();
				String enterNoOfSeatsMessege = null;
				if (option == 1) {
					enterNoOfSeatsMessege = "Enter Number of Seats, when pressing enter the request will be triggered:";
				} else {
					enterNoOfSeatsMessege = "Enter Number of Seats:";
				}
				System.out.print(enterNoOfSeatsMessege);

				int noOfSeats = in.nextInt();

				BusReservation busReservation = new BusReservation();
				busReservation.setDate(date);
				busReservation.setStart(start);
				busReservation.setDestination(destination);
				busReservation.setNoOfSeats(noOfSeats);
				String jsonString = Common.getGsonObject().toJson(busReservation);
				if (option == 1) {
					System.out.println("********Response*******************************");
					System.out.print(makeRequest(URLInput, "POST", jsonString));
					System.out.println("***********************************************");
				} else if (option == 2) {
					System.out.print("Enter no of Reservation Attempts, when pressing enter the request/s will be triggered:");
					int attempts = in.nextInt();
					for (int i = 0; i < attempts; i++) {
						Thread.sleep(2000);
						System.out.println("********Response*******************************");
						System.out.print(makeRequest(URLInput, "PUT", jsonString));
						System.out.println("***********************************************");
					}
				}
				System.out.print("Enter 'exit' or 'e' to stop or any input to retry:");
				String exitInput = in.next();
				if (exitInput != null && (exitInput.equalsIgnoreCase("exit") || exitInput.equalsIgnoreCase("e"))) {
					exit = true;
				}
			}

		} finally {
			in.close();
		}

	}

	private static String makeRequest(String URLInput, String HTTPMethod, String payload) throws IOException {
		URL myurl = new URL(URLInput);
		HttpURLConnection httpURLConnection = (HttpURLConnection) myurl.openConnection();

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod(HTTPMethod);
		httpURLConnection.setRequestProperty("Content-Type", "application/json");

		try (DataOutputStream writer = new DataOutputStream(httpURLConnection.getOutputStream())) {

			writer.write(payload.getBytes());
		}

		StringBuilder responseStringBuilder;
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(httpURLConnection.getInputStream()))) {
			String line;
			responseStringBuilder = new StringBuilder();
			responseStringBuilder.append("Response Code:");
			responseStringBuilder.append(httpURLConnection.getResponseCode());
			responseStringBuilder.append(System.lineSeparator());
			while ((line = bufferedReader.readLine()) != null) {
				responseStringBuilder.append(line);
				responseStringBuilder.append(System.lineSeparator());
			}
		}

		return responseStringBuilder.toString();
	}

	private static void disableSSLVerification() throws NoSuchAlgorithmException, KeyManagementException {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

}
