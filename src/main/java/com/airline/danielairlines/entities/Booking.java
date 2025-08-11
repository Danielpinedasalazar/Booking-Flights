package com.airline.danielairlines.entities;

import com.airline.danielairlines.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String bookingReference;

    //Muchas reservas pueden pertenecer a un usuario
    @ManyToOne
    private User user;

    //Muchas reservas pueden pertenecer a un vuelo
    @ManyToOne
    private Flight flight;

    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    //Una reserva puede pertencer a muchos pasageros
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Passenger> passenger = new ArrayList<>();
}
