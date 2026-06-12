package com.flight.repository;

import com.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;


public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOriginAndDestinationAndDepartureDateTimeBetween(
            String origin, String destination, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
