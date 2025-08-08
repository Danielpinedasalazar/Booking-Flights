package com.airline.danielairlines.dtos;

import com.airline.danielairlines.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {

    private long id;

    private String flightNumber;

    private FlightStatus status;

    private AirportDTO departureAirport;

    private AirportDTO arrivalAirport;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private BigDecimal basePrice;

    private UserDTO assignedPilot;

    private List<BookingDTO> bookings;

    private String departureAirportIataCode;

    private String arrivalAirportIataCode;
}
