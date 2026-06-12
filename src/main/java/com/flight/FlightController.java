package com.flight;

import com.flight.entity.Flight;
import com.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    private static final Logger log = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    @GetMapping
    public List<Flight> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Searching flights from {} to {} on {}", origin, destination, date);

        List<Flight> flights = flightService.searchFlights(origin, destination, date);
        log.info("Found {} flights", flights.size());
        return flights;
    }
}