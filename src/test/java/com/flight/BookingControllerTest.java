package com.flight;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.dto.BookingRequest;
import com.flight.entity.Booking;
import com.flight.entity.Flight;
import com.flight.repository.BookingRepository;
import com.flight.service.BookingService;
import com.flight.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightService flightService;   // ← Mock the dependency

    @InjectMocks
    private BookingService bookingService;

    @Test
    void createBooking_ShouldSucceed_WhenSeatsAvailable() {
        // given
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setAvailableSeats(100);
        when(flightService.getFlightById(1L)).thenReturn(flight);
        when(flightService.saveFlight(any(Flight.class))).thenReturn(flight);

        Booking booking = new Booking("John", flight, 2);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // when
        Booking result = bookingService.createBooking(1L, "John", 2);

        // then
        verify(flightService).saveFlight(flight);   // verify mock was called
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void createBooking_ShouldThrow_WhenNotEnoughSeats() {
        Flight flight = new Flight();
        flight.setAvailableSeats(1);
        when(flightService.getFlightById(1L)).thenReturn(flight);

        assertThatThrownBy(() -> bookingService.createBooking(1L, "John", 5))
                .isInstanceOf(RuntimeException.class);

        verify(flightService, never()).saveFlight(any());
        verify(bookingRepository, never()).save(any());
    }

}