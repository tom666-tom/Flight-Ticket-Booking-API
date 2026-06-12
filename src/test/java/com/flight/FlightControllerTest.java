package com.flight;

import com.flight.FlightController;
import com.flight.entity.Flight;
import com.flight.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Test
    void searchFlights_ShouldReturnListOfFlights() throws Exception {
        // Given
        Flight flight = new Flight("AA101", "New York", "London",
                LocalDateTime.of(2026, 6, 20, 10, 0), 150, 150, 450.0);
        when(flightService.searchFlights(eq("New York"), eq("London"), any()))
                .thenReturn(List.of(flight));

        // When & Then
        mockMvc.perform(get("/api/flights")
                        .param("origin", "New York")
                        .param("destination", "London")
                        .param("date", "2026-06-20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].flightNumber").value("AA101"))
                .andExpect(jsonPath("$[0].origin").value("New York"))
                .andExpect(jsonPath("$[0].destination").value("London"));

        // Verify the service method was called exactly once with correct parameters
        verify(flightService).searchFlights(eq("New York"), eq("London"), any());
    }

    @Test
    void searchFlights_ShouldReturnEmptyList_WhenNoFlightsMatch() throws Exception {
        // Given
        when(flightService.searchFlights(any(), any(), any())).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/flights")
                        .param("origin", "Paris")
                        .param("destination", "Tokyo")
                        .param("date", "2026-06-20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(flightService).searchFlights(eq("Paris"), eq("Tokyo"), any());
    }

    @Test
    void searchFlights_ShouldReturnBadRequest_WhenMissingParameters() throws Exception {
        // When & Then - missing destination parameter
        mockMvc.perform(get("/api/flights")
                        .param("origin", "New York")
                        .param("date", "2026-06-20"))
                .andExpect(status().isBadRequest());

        // Service should never be called
        verify(flightService, org.mockito.Mockito.never())
                .searchFlights(any(), any(), any());
    }
}