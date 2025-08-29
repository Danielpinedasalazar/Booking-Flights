package com.airline.danielairlines.services.impl;

import com.airline.danielairlines.dtos.CreateFlightRequest;
import com.airline.danielairlines.dtos.FlightDTO;
import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.entities.Airport;
import com.airline.danielairlines.entities.Flight;
import com.airline.danielairlines.entities.User;
import com.airline.danielairlines.enums.City;
import com.airline.danielairlines.enums.Country;
import com.airline.danielairlines.enums.FlightStatus;
import com.airline.danielairlines.exceptions.BadRequestException;
import com.airline.danielairlines.exceptions.NotFoundException;
import com.airline.danielairlines.repo.AirportRepo;
import com.airline.danielairlines.repo.FlightRepo;
import com.airline.danielairlines.repo.UserRepo;
import com.airline.danielairlines.services.FlightService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepo flightRepo;
    private final UserRepo userRepo;
    private final AirportRepo airportRepo;
    private final ModelMapper modelMapper;

    @Override
    public Response<?> createFlight(CreateFlightRequest createFlightRequest) {

        if(createFlightRequest.getArrivalTime().isBefore(createFlightRequest.getDepartureTime())) {
            throw new BadRequestException("Departure time must be before arrival time");
        }

        if(flightRepo.existsByFlightNumber(createFlightRequest.getFlightNumber())) {
            throw new BadRequestException("Flight number already exists");
        }

        //fetch and validate the departure airport
        Airport departureAirport = airportRepo.findByIataCode(createFlightRequest.getDepartureAirportIataCode())
                    .orElseThrow(() -> new NotFoundException("Departure Airport does not exist"));

        Airport arrivalAirport = airportRepo.findByIataCode(createFlightRequest.getArrivalAirportIataCode())
                .orElseThrow(() -> new NotFoundException("Departure Airport does not exist"));

        Flight flightToSave = new Flight();

        flightToSave.setFlightNumber(createFlightRequest.getFlightNumber());
        flightToSave.setDepartureAirport(departureAirport);
        flightToSave.setArrivalAirport(arrivalAirport);
        flightToSave.setDepartureTime(createFlightRequest.getDepartureTime());
        flightToSave.setArrivalTime(createFlightRequest.getArrivalTime());
        flightToSave.setBasePrice(createFlightRequest.getBasePrice());
        flightToSave.setStatus(FlightStatus.SCHEDULED);

        //assign pilots to the flight
        if(createFlightRequest.getPilotId() != null){

            User pilot = userRepo.findById(createFlightRequest.getPilotId())
                    .orElseThrow(() -> new NotFoundException("Pilot not found"));

            boolean isPilot = pilot.getRoles().stream()
                    .anyMatch(role -> role.getName().equals("PILOT"));

            if(!isPilot){
                throw new BadRequestException("Pilot role not supported");
            }
            flightToSave.setAssignedPilot(pilot);
        }

        //save the flight
        flightRepo.save(flightToSave);

        return Response.builder()
                .statusCode(201)
                .message("Flight created successfully")
                .build();
    }

    @Override
    public Response<FlightDTO> getFlightById(Long id) {
        Flight flight = flightRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight not found"));

        FlightDTO flightDTO = modelMapper.map(flight, FlightDTO.class);

        if(flightDTO.getBookings() != null){
            flightDTO.getBookings().forEach(booking -> booking.setFlight(null));
        }

        return Response.<FlightDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Flight retrieved successfully")
                .data(flightDTO)
                .build();
    }

    @Override
    public Response<List<FlightDTO>> getAllFlights() {
        Sort sortByIdDesc = Sort.by(Sort.Direction.DESC, "id");

        List<FlightDTO> flights = flightRepo.findAll(sortByIdDesc).stream()
                .map(flight -> {
                    FlightDTO flightDTO = modelMapper.map(flight, FlightDTO.class);
                    if (flightDTO.getBookings() != null){
                        flightDTO.getBookings().forEach(bookingDTO -> bookingDTO.setFlight(null));
                    }
                    return flightDTO;
                }).toList();

        return Response.<List<FlightDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(flights.isEmpty() ? "No Flights Found" : "Flights retrieved successfully")
                .data(flights)
                .build();
    }

    @Override
    @Transactional
    public Response<?> updateFlight(CreateFlightRequest flightRequest) {
        Long id = flightRequest.getPilotId();

        Flight existingFlight = flightRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight not found"));

        if(flightRequest.getDepartureTime() != null){
            existingFlight.setDepartureTime(flightRequest.getDepartureTime());
        }

        if(flightRequest.getArrivalTime() != null){
            existingFlight.setArrivalTime(flightRequest.getArrivalTime());
        }

        if(flightRequest.getBasePrice() != null){
            existingFlight.setBasePrice(flightRequest.getBasePrice());
        }

        if(flightRequest.getStatus() != null){
            existingFlight.setStatus(flightRequest.getStatus());
        }

        //if pilot id is passed in validate the pilot and update it
        if (flightRequest.getPilotId() != null){
            User pilot = userRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Pilot not found"));

            boolean isPilot = pilot.getRoles().stream()
                    .anyMatch(role -> role.getName().equalsIgnoreCase("PILOT"));

            if (!isPilot){
                throw new BadRequestException("Pilot role not supported");
            }

            existingFlight.setAssignedPilot(pilot);
        }

        flightRepo.save(existingFlight);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Flight updated successfully")
                .build();
    }

    @Override
    public Response<List<FlightDTO>> searchFlights(String departureAirportIata, String arrivalAirportIata, FlightStatus flightStatus, LocalDate departureDate) {
        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime endOfDay = departureDate.plusDays(1).atStartOfDay().minusNanos(1); // 23:59:59.999999999

        List<Flight> flights = flightRepo.findByDepartureAirportIataCodeAndArrivalAirportIataCodeAndStatusAndDepartureTimeBetween(
                departureAirportIata,
                arrivalAirportIata,
                flightStatus,
                startOfDay,
                endOfDay
        );

        List<FlightDTO> flightDTOs = flights.stream()
                .map(flight -> {
                    FlightDTO flightDTO = modelMapper.map(flight, FlightDTO.class);
                    flightDTO.setAssignedPilot(null);
                    flightDTO.setBookings(null);
                    return flightDTO;
                }).toList();

        return Response.<List<FlightDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(flightDTOs.isEmpty() ? "No Flights Found" : "Flights retrieved successfully")
                .data(flightDTOs)
                .build();
    }

    @Override
    public Response<List<City>> getAllCities() {
        return Response.<List<City>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cities retrieved successfully")
                .data(List.of(City.values()))
                .build();
    }

    @Override
    public Response<List<Country>> getAllCountries() {
        return Response.<List<Country>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cities retrieved successfully")
                .data(List.of(Country.values()))
                .build();
    }
}
