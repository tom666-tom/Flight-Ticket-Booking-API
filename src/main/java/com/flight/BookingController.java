package com.flight;

import com.flight.dto.BookingRequest;
import com.flight.dto.BookingResponse;
import com.flight.entity.Booking;
import com.flight.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(@Valid @RequestBody BookingRequest request) {
        log.info("Creating booking for flightId={}, passenger={}, seats={}",
                request.getFlightId(), request.getPassengerName(), request.getSeatsBooked());
        Booking booking = bookingService.createBooking(
                request.getFlightId(),
                request.getPassengerName(),
                request.getSeatsBooked()
        );
        log.info("Booking created with id={}, reference={}", booking.getId(), booking.getBookingReference());
        return BookingResponse.fromEntity(booking);
    }

    @GetMapping("/{bookingReference}")
    public BookingResponse getBooking(@PathVariable String bookingReference) {
        Booking booking = bookingService.getBookingByReference(bookingReference);
        log.debug("Retrieved booking: reference={}, passenger={}, status={}",
                booking.getBookingReference(), booking.getPassengerName(), booking.getStatus());
        return BookingResponse.fromEntity(booking);
    }

    @DeleteMapping("/{bookingReference}")
    public BookingResponse cancelBooking(@PathVariable String bookingReference) {
        log.info("Attempting to cancel booking with reference: {}", bookingReference);
        Booking cancelled = bookingService.cancelBooking(bookingReference);
        log.info("Booking with id: {} cancelled successfully. New status: {}",
                cancelled.getId(), cancelled.getStatus());
        return BookingResponse.fromEntity(cancelled);
    }
}