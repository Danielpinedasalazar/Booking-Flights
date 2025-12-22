package com.airline.danielairlines.services;

import com.airline.danielairlines.dtos.AirportDTO;
import com.airline.danielairlines.dtos.Response;

import java.util.List;

public interface AirportsService {

    Response<?> createAirport(AirportDTO airportDTO);

    Response<?> updateAirport(Long id, AirportDTO airportDTO);

    Response<List<AirportDTO>> getAllAirports();

    Response<AirportDTO> getAirportById(Long id);
}
