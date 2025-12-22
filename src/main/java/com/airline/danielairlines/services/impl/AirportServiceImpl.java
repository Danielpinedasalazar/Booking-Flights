package com.airline.danielairlines.services.impl;

import com.airline.danielairlines.dtos.AirportDTO;
import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.entities.Airport;
import com.airline.danielairlines.enums.City;
import com.airline.danielairlines.enums.Country;
import com.airline.danielairlines.exceptions.BadRequestException;
import com.airline.danielairlines.exceptions.NotFoundException;
import com.airline.danielairlines.repo.AirportRepo;
import com.airline.danielairlines.services.AirportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportsService {

    private final AirportRepo airportRepo;
    private final ModelMapper modelMapper;


    @Override
    public Response<?> createAirport(AirportDTO airportDTO) {
        log.info("Inside createAirport()");

        Country country = airportDTO.getCountry();
        City city = airportDTO.getCity();

        if (!city.getCountry().equals(country)){
            throw new BadRequestException("CITY does not belong to the country");
        }

        Airport airport = modelMapper.map(airportDTO, Airport.class);
        airportRepo.save(airport);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Airport Created Successfully")
                .build();


    }

    @Override
    public Response<?> updateAirport(Long id, AirportDTO airportDTO) {
        // ✅ CORREGIDO: Ahora el id viene como parámetro, no del DTO
        Airport existingAirport = airportRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Airport Not Found"));

        // Validar que la ciudad pertenece al país
        if (airportDTO.getCity() != null) {
            if (!airportDTO.getCity().getCountry().equals(existingAirport.getCountry())) {
                throw new BadRequestException("CITY does not belong to the country");
            }
            existingAirport.setCity(airportDTO.getCity());
        }

        if (airportDTO.getName() != null) {
            existingAirport.setName(airportDTO.getName());
        }

        if (airportDTO.getIataCode() != null) {
            existingAirport.setIataCode(airportDTO.getIataCode());
        }

        // ✅ OPCIONAL: Si quieres permitir actualizar el país
        if (airportDTO.getCountry() != null) {
            existingAirport.setCountry(airportDTO.getCountry());
        }

        airportRepo.save(existingAirport);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Airport updated Successfully")
                .build();
    }

    @Override
    public Response<List<AirportDTO>> getAllAirports() {

        List<AirportDTO> airports = airportRepo.findAll().stream()
                .map(airport -> modelMapper.map(airport, AirportDTO.class))
                .toList();

        return Response.<List<AirportDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(airports.isEmpty() ? "No Airports Found": "Airports retrieved successfully")
                .data(airports)
                .build();
    }

    @Override
    public Response<AirportDTO> getAirportById(Long id) {

        Airport airport = airportRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("Airport Not Found"));

        AirportDTO airportDTO = modelMapper.map(airport, AirportDTO.class);

        return Response.<AirportDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message( "Airport retrieved successfully")
                .data(airportDTO)
                .build();

    }
}
