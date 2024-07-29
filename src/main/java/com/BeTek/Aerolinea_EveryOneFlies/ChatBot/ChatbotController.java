package com.BeTek.Aerolinea_EveryOneFlies.ChatBot;

import com.BeTek.Aerolinea_EveryOneFlies.Models.Flight;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Passenger;
import com.BeTek.Aerolinea_EveryOneFlies.Models.Reservation;
import com.BeTek.Aerolinea_EveryOneFlies.Services.FlightService;
import com.BeTek.Aerolinea_EveryOneFlies.Services.PassangerServices;
import com.BeTek.Aerolinea_EveryOneFlies.Services.ReservationService;
import com.google.cloud.dialogflow.v2.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("api/v1/chatbot")
public class ChatbotController {


    private SessionsClient sessionsClient;
    private FlightService flightService;
    private ReservationService reservationService;
    private UserSessionManager userSessionManager;
    private Map<String, Boolean> userStates = new HashMap<>();
    private PassangerServices passangerServices;


    @Autowired

    public ChatbotController(SessionsClient sessionsClient, FlightService flightService, ReservationService reservationService, UserSessionManager userSessionManager, Map<String, Boolean> userStates, PassangerServices passangerServices) {
        this.sessionsClient = sessionsClient;
        this.flightService = flightService;
        this.reservationService = reservationService;
        this.userSessionManager = userSessionManager;
        this.userStates = userStates;
        this.passangerServices = passangerServices;
    }

    @PostMapping("/message")
    public String sendMessage(@RequestParam String message, HttpSession httpSession) {
        String userId = (String) httpSession.getAttribute("userId");
        if (userId == null) {
            userId = UUID.randomUUID().toString();
            httpSession.setAttribute("userId", userId);
            userSessionManager.setSessionIdForUser(userId, UUID.randomUUID().toString());
        }

        String sessionId = userSessionManager.getSessionIdForUser(userId);
        String projectId = "everyoneflies-ewlv";
        SessionName session = SessionName.of(projectId, sessionId);

        TextInput.Builder textInput = TextInput.newBuilder().setText(message).setLanguageCode("en-US");
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
        DetectIntentRequest request = DetectIntentRequest.newBuilder()
                .setSession(session.toString())
                .setQueryInput(queryInput)
                .build();

        DetectIntentResponse response = sessionsClient.detectIntent(request);
        QueryResult queryResult = response.getQueryResult();
        String action = queryResult.getAction();

        if ("VerVuelos".equals(action)) {
            return getAvailableFlights();
        } else if ("HacerReserva".equals(action) || "input.unknown".equals(action)) {
            return handleReservation(userId, message);
        }

        return queryResult.getFulfillmentText();
    }

    private String getAvailableFlights() {
        List<Flight> flights = flightService.getAllFlights();
        StringBuilder flightDetails = new StringBuilder("Vuelos disponibles:\n");
        for (Flight flight : flights) {
            flightDetails.append("Vuelo: \n")
                    .append("ID: ").append(flight.getId())
                    .append("\n")
                    .append("Origen: ").append(flight.getOriginAirport())
                    .append("\n")
                    .append("Destino: ").append(flight.getDestinationAirport())
                    .append("\n")
                    .append("Fecha de salida: ").append(flight.getDepartureDate())
                    .append("\n")
                    .append("Hora de salida: ").append(flight.getDepartureTime())
                    .append("\n")
                    .append("\n");
        }
        return flightDetails.toString();
    }

    private String handleReservation(String userId, String message) {
        if (userStates.getOrDefault(userId, false)) {
            try {
                Reservation reservation = parseReservationData(message);
                reservationService.createReservation(reservation);
                userStates.put(userId, false);
                return "Reserva realizada con éxito. \n" +
                        "*** Gracias por volar con EveryOne Flies ***\n" +
                        "            Fly High, Fly With Us            ";
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return "Formato de entrada inválido. Por favor, asegúrate de seguir el formato correcto.";
            } catch (Exception e) {
                e.printStackTrace();
                return "Hubo un problema al procesar la reserva. Por favor, verifica los datos e intenta nuevamente.";
            }
        } else {
            userStates.put(userId, true);
            return "Por favor, ingresa los datos de la reserva en el siguiente formato: dni, name, lastName, email, birthDate, numberCel, flightId, category.";
        }
    }

    private Reservation parseReservationData(String inputData) {
        String[] data = inputData.split(",");


        if (data.length != 8) {
            throw new IllegalArgumentException("Formato de entrada inválido. Se esperaba 8 campos.");
        }

        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDate.now());
        reservation.setSeat(1);

        Passenger passenger = new Passenger();
        passenger.setDni(data[0].trim());
        passenger.setName(data[1].trim());
        passenger.setLastName(data[2].trim());
        passenger.setEmail(data[3].trim());
        passenger.setBirthDate(LocalDate.parse(data[4].trim()));
        passenger.setNumberCel(Integer.parseInt(data[5].trim()));


        List<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);
        reservation.setPassengers(passengers);



        reservation.setFlight(new Flight());
        reservation.getFlight().setId(Integer.parseInt(data[6].trim()));
        reservation.setCategory(data[7].trim());

        return reservation;
    }
}