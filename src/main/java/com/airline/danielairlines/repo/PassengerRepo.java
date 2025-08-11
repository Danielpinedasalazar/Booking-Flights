package com.airline.danielairlines.repo;

import com.airline.danielairlines.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepo extends JpaRepository<Passenger, Long> {


}
