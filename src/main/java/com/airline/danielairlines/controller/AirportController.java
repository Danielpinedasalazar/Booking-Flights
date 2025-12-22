package com.airline.danielairlines.controller;

import com.airline.danielairlines.dtos.AirportDTO;
import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.services.AirportsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportsService airportsService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> createAirport(@Valid @RequestBody AirportDTO airportDTO) {
        return ResponseEntity.ok(airportsService.createAirport(airportDTO));
    }

    // ✅ CORREGIDO: Ahora usa /{id} en la URL
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> updateAirport(
            @PathVariable Long id,
            @Valid @RequestBody AirportDTO airportDTO
    ) {
        return ResponseEntity.ok(airportsService.updateAirport(id, airportDTO));
    }

    // ✅ GET endpoints - públicos (sin @PreAuthorize)
    @GetMapping
    public ResponseEntity<Response<List<AirportDTO>>> getAllAirports() {
        return ResponseEntity.ok(airportsService.getAllAirports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<AirportDTO>> getAirportById(@PathVariable Long id) {
        return ResponseEntity.ok(airportsService.getAirportById(id));
    }
}