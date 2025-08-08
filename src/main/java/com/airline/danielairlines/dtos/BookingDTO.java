package com.airline.danielairlines.dtos;

import com.airline.danielairlines.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private long id;
    private String bookingReference;

    private UserDTO user;

    private FlightDTO flight;

    private LocalDateTime bookingDate;

    private BookingStatus bookingStatus;

    private List<PassengerDTO> passenger;
}
