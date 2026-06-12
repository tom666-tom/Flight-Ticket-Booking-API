package com.flight;

import com.flight.entity.Flight;
import com.flight.service.FlightService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDateTime;


@SpringBootApplication
public class FlightApplication implements CommandLineRunner {

    @Autowired
    private FlightService flightService;

    public static void main(String[] args) {
        SpringApplication.run(FlightApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Flight flight1 = new Flight("AA101", "New York", "London",
                LocalDateTime.of(2026, 6, 20, 10, 0), 150, 150, 450.00);
        Flight flight2 = new Flight("BA202", "London", "Paris",
                LocalDateTime.of(2026, 6, 21, 14, 30), 80, 80, 120.00);
        Flight flight3 = new Flight("DL303", "New York", "Los Angeles",
                LocalDateTime.of(2026, 6, 22, 8, 15), 200, 200, 300.00);

        flightService.saveFlight(flight1);
        flightService.saveFlight(flight2);
        flightService.saveFlight(flight3);

        System.out.println("Sample flights loaded.");

    }
}

