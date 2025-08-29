package com.airline.danielairlines.services;

import com.airline.danielairlines.dtos.CreateFlightRequest;
import com.airline.danielairlines.dtos.FlightDTO;
import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.enums.City;
import com.airline.danielairlines.enums.Country;
import com.airline.danielairlines.enums.FlightStatus;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {

    Response<?> createFlight(CreateFlightRequest createFlightRequest);
    Response<FlightDTO> getFlightById(Long id);
    Response<List<FlightDTO>> getAllFlights();
    Response<?> updateFlight(CreateFlightRequest createFlightRequest);
    Response<List<FlightDTO>> searchFlights(String departureAirportIata, String arrivalAirportIata, FlightStatus flightStatus, LocalDate departureDate);
    Response<List<City>> getAllCities();
    Response<List<Country>> getAllCountries();

}
