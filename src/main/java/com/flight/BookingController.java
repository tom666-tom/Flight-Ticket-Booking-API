package com.flight;

import com.flight.dto.BookingRequest;
import com.flight.dto.BookingResponse;
import com.flight.entity.Booking;
import com.flight.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(@Valid @RequestBody BookingRequest request) {
        Booking booking = bookingService.createBooking(
                request.getFlightId(),
                request.getPassengerName(),
                request.getSeatsBooked()
        );
        return BookingResponse.fromEntity(booking);
    }

    @GetMapping("/{id}")
    public BookingResponse getBooking(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return BookingResponse.fromEntity(booking);
    }

    @DeleteMapping("/{id}")
    public BookingResponse cancelBooking(@PathVariable Long id) {
        Booking cancelled = bookingService.cancelBooking(id);
        return BookingResponse.fromEntity(cancelled);
    }
}