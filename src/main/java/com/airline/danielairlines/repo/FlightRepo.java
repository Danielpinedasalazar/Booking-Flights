package com.airline.danielairlines.repo;

import com.airline.danielairlines.entities.Flight;
import com.airline.danielairlines.enums.FlightStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepo extends JpaRepository<Flight, Long> {

    // ✅ OPTIMIZADO: Carga eager de airports y pilot en una sola query
    @EntityGraph(attributePaths = {"departureAirport", "arrivalAirport", "assignedPilot"})
    List<Flight> findAll(Sort sort);

    // ✅ OPTIMIZADO: Para cuando necesitas un vuelo específico con sus relaciones
    @EntityGraph(attributePaths = {"departureAirport", "arrivalAirport", "assignedPilot"})
    Optional<Flight> findById(Long id);

    // ✅ OPTIMIZADO: Búsqueda de vuelos con airports cargados
    @EntityGraph(attributePaths = {"departureAirport", "arrivalAirport", "assignedPilot"})
    List<Flight> findByDepartureAirportIataCodeAndArrivalAirportIataCodeAndStatusAndDepartureTimeBetween(
            String departureAirportIata,
            String arrivalAirportIata,
            FlightStatus status,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );

    // Método para verificar si existe un número de vuelo
    boolean existsByFlightNumber(String flightNumber);

    // ✅ OPCIONAL: Si necesitas cargar también los bookings (solo para detalles)
    @Query("SELECT f FROM Flight f " +
            "LEFT JOIN FETCH f.departureAirport " +
            "LEFT JOIN FETCH f.arrivalAirport " +
            "LEFT JOIN FETCH f.assignedPilot " +
            "LEFT JOIN FETCH f.bookings b " +
            "LEFT JOIN FETCH b.user " +
            "WHERE f.id = :id")
    Optional<Flight> findByIdWithBookings(@Param("id") Long id);
}