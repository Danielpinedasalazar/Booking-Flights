package com.airline.danielairlines.controller;

import com.airline.danielairlines.dtos.CreateFlightRequest;
import com.airline.danielairlines.dtos.FlightDTO;
import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.enums.City;
import com.airline.danielairlines.enums.Country;
import com.airline.danielairlines.enums.FlightStatus;
import com.airline.danielairlines.services.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','PILOT')")
    public ResponseEntity<Response<?>> createFlight(@Valid @RequestBody CreateFlightRequest createFlightRequest) {
        return ResponseEntity.ok(flightService.createFlight(createFlightRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getFlightById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<FlightDTO>>> getAllFlights(){
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @PutMapping("/flights/{id}")
    public ResponseEntity<Response<?>> updateFlight(
            @PathVariable Long id,
            @RequestBody CreateFlightRequest request
    ) {
        return ResponseEntity.ok(flightService.updateFlight(id, request));
    }

    @GetMapping("/search")
    public ResponseEntity<Response<List<FlightDTO>>> searchFlights(
            @RequestParam(required = true) String departureAirportIata,
            @RequestParam(required = true) String arrivalAirportIata,
            @RequestParam(required = false, defaultValue = "SCHEDULED") FlightStatus flightStatus,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate
    ){
        return ResponseEntity.ok(flightService.searchFlights(departureAirportIata, arrivalAirportIata, flightStatus, departureDate));
    }

    @GetMapping("/cities")
    public ResponseEntity<Response<List<City>>> getAllCities() {
        return ResponseEntity.ok(flightService.getAllCities());
    }

    @GetMapping("/countries")
    public ResponseEntity<Response<List<Country>>> getAllCountries() {
        return ResponseEntity.ok(flightService.getAllCountries());
    }
}
