package com.airline.danielairlines.services;

import com.airline.danielairlines.dtos.BookingDTO;
import com.airline.danielairlines.dtos.CreateBookingRequest;
import com.airline.danielairlines.dtos.Response;
import com.airline.danielairlines.enums.BookingStatus;

import java.util.List;

public interface BookingService {

    Response<?> createBooking(CreateBookingRequest createBookingRequest);
    Response<BookingDTO> getBookingById(Long id);
    Response<List<BookingDTO>> getAllBookings();
    Response<List<BookingDTO>> getMyBookings();
    Response<?> updateBookingStatus(Long id, BookingStatus status);
}
