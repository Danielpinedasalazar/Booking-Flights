package com.airline.danielairlines.entities;

import com.airline.danielairlines.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "flights")
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String flightNumber;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    // ✅ OPTIMIZADO: Agregado fetch = FetchType.LAZY
    // Se cargarán con @EntityGraph cuando los necesites
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_airport_id")
    private Airport arrivalAirport;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private BigDecimal basePrice;

    // ✅ OPTIMIZADO: Agregado fetch = FetchType.LAZY
    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedPilot;

    // ✅ CRÍTICO: Bookings DEBE ser LAZY para evitar el problema N+1
    // Solo cárgalos cuando realmente los necesites (ej: detalle de vuelo)
    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();
}