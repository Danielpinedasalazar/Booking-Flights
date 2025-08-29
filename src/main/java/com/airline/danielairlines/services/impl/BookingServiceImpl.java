package com.airline.danielairlines.services.impl;

import com.airline.danielairlines.dtos.BookingDTO;
import com.airline.danielairlines.dtos.CreateBookingRequest;
import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.entities.Booking;
import com.airline.danielairlines.entities.Flight;
import com.airline.danielairlines.entities.Passenger;
import com.airline.danielairlines.entities.User;
import com.airline.danielairlines.enums.BookingStatus;
import com.airline.danielairlines.enums.FlightStatus;
import com.airline.danielairlines.exceptions.BadRequestException;
import com.airline.danielairlines.exceptions.NotFoundException;
import com.airline.danielairlines.repo.BookingRepo;
import com.airline.danielairlines.repo.FlightRepo;
import com.airline.danielairlines.repo.PassengerRepo;
import com.airline.danielairlines.services.BookingService;
import com.airline.danielairlines.services.EmailNotificationService;
import com.airline.danielairlines.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final UserService userService;
    private final FlightRepo flightRepo;
    private final PassengerRepo passengerRepo;
    private final ModelMapper modelMapper;
    private final EmailNotificationService emailNotificationService;


    @Override
    @Transactional
    public Response<?> createBooking(CreateBookingRequest createBookingRequest) {
        User user = userService.currentUser();

        Flight flight = flightRepo.findById(createBookingRequest.getFlightId())
                .orElseThrow(() -> new NotFoundException("Flight not found"));

        if(flight.getStatus() != FlightStatus.SCHEDULED) {
            throw new BadRequestException("Flight is not scheduled");
        }

        Booking booking = new Booking();
        booking.setBookingReference(generateBookingReference());
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setBookingDate(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepo.save(booking);

        if(createBookingRequest.getPassengers() != null && !createBookingRequest.getPassengers().isEmpty()) {

            List<Passenger> passengers = createBookingRequest.getPassengers().stream()
                    .map(passengerDTO -> {
                        Passenger passenger = modelMapper.map(passengerDTO, Passenger.class);
                        passenger.setBooking(savedBooking);
                        return passenger;
                    }).toList();

            passengerRepo.saveAll(passengers);
            savedBooking.setPassengers(passengers);
        }

        //Send email ticket out
        emailNotificationService.sendBooingTicketsEmail(savedBooking);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Booking created successfully")
                .build();
    }

    @Override
    public Response<BookingDTO> getBookingById(Long id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
        bookingDTO.getFlight().setBookings(null);

        return Response.<BookingDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Booking retrieved successfully")
                .data(bookingDTO)
                .build();

    }

    @Override
    public Response<List<BookingDTO>> getAllBookings() {
        List<Booking> allBookings = bookingRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<BookingDTO> bookings = allBookings.stream()
                .map(booking -> {;
                    BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
                    bookingDTO.getFlight().setBookings(null);
                    return bookingDTO;
                }).toList();

        return Response.<List<BookingDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(bookings.isEmpty() ? "No bookings found" : "Bookings retrieved successfully")
                .data(bookings)
                .build();
    }

    @Override
    public Response<List<BookingDTO>> getMyBookings() {
        User user = userService.currentUser();
        List<Booking> userBookings = bookingRepo.findByUserIdOrderByIdDesc(user.getId());

        List<BookingDTO> bookings = userBookings.stream()
                .map(booking -> {;
                    BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
                    bookingDTO.getFlight().setBookings(null);
                    return bookingDTO;
                }).toList();

        return Response.<List<BookingDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(bookings.isEmpty() ? "No bookings found" : "Bookings retrieved successfully")
                .data(bookings)
                .build();

    }

    @Override
    @Transactional
    public Response<?> updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
        booking.setBookingStatus(status);
        bookingRepo.save(booking);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Booking status updated successfully")
                .build();
    }

    //Implement to make sure the booking reference does not already exist
    private String generateBookingReference() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
