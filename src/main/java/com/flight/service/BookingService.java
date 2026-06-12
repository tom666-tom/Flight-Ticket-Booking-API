package com.flight.service;

import com.flight.entity.Booking;
import com.flight.entity.Flight;
import com.flight.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightService flightService;

    @Transactional
    public Booking createBooking(Long flightId, String passengerName, Integer seatsBooked) {
        Flight flight = flightService.getFlightById(flightId);

        if (flight.getAvailableSeats() < seatsBooked) {
            log.warn("Insufficient seats for flight {}: requested {}, available {}",
                    flightId, seatsBooked, flight.getAvailableSeats());
            throw new RuntimeException(
                    String.format("Only %d seats available, but you requested %d",
                            flight.getAvailableSeats(), seatsBooked));
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - seatsBooked);
        flightService.saveFlight(flight);

        Booking booking = new Booking(passengerName, flight, seatsBooked);
        return bookingRepository.save(booking);
    }

    public Booking getBookingByReference(String bookingReference) {
        log.debug("Fetching booking with reference={}", bookingReference);
        return bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new RuntimeException(
                        "Booking not found with reference: " + bookingReference));
    }

    @Transactional
    public Booking cancelBooking(String bookingReference) {
        log.info("Cancelling booking reference={}", bookingReference);
        Booking booking = getBookingByReference(bookingReference);

        if ("CANCELLED".equals(booking.getStatus())) {
            log.info("Booking {} already cancelled, no action taken", bookingReference);
            return booking;
        }

        Flight flight = booking.getFlight();
        int restoredSeats = booking.getSeatsBooked();
        flight.setAvailableSeats(flight.getAvailableSeats() + restoredSeats);
        flightService.saveFlight(flight);

        booking.setStatus("CANCELLED");
        Booking cancelled = bookingRepository.save(booking);

        log.info("Booking {} cancelled, restored {} seats to flight {}",
                bookingReference, restoredSeats, flight.getId());
        return cancelled;
    }
}