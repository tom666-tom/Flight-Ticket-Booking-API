package com.flight.dto;

import com.flight.entity.Booking;
import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private String bookingReference;
    private String passengerName;
    private Long flightId;
    private String flightNumber;
    private Integer seatsBooked;
    private String status;
    private LocalDateTime bookingDateTime;

    public static BookingResponse fromEntity(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setBookingReference(booking.getBookingReference());
        response.setPassengerName(booking.getPassengerName());
        response.setFlightId(booking.getFlight().getId());
        response.setFlightNumber(booking.getFlight().getFlightNumber());
        response.setSeatsBooked(booking.getSeatsBooked());
        response.setStatus(booking.getStatus());
        response.setBookingDateTime(booking.getBookingDateTime());
        return response;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public Integer getSeatsBooked() { return seatsBooked; }
    public void setSeatsBooked(Integer seatsBooked) { this.seatsBooked = seatsBooked; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getBookingDateTime() { return bookingDateTime; }
    public void setBookingDateTime(LocalDateTime bookingDateTime) { this.bookingDateTime = bookingDateTime; }
}
