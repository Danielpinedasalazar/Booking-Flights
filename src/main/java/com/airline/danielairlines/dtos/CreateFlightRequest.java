package com.airline.danielairlines.dtos;

import com.airline.danielairlines.enums.FlightStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFlightRequest {

    private FlightStatus status;

    @NotBlank(message = "Flight number cannot be blank")
    private String flightNumber;

    @NotBlank(message = "Departure airport IATA cannot be blank")
    private String departureAirportIataCode;

    @NotBlank(message = "Departure airport IATA cannot be blank")
    private String arrivalAirportIataCode;

    @NotNull(message = "Departure time cannot be blank")
    private LocalDateTime departureTime;

    @NotNull(message = "Departure time cannot be null")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Base price cannot be null")
    @Positive(message = "Base price must be positive")
    private BigDecimal basePrice;

    private Long pilotId;
}
