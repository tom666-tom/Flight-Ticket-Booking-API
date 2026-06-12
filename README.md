# Flight Ticket Booking API

A simple REST API for searching flights, booking tickets, viewing bookings, and cancelling reservations.  
Built with **Spring Boot 3.x**, **Gradle**, **H2 in‑memory database**, and **Java 17**.

---

## How to run the service

### Prerequisites
- Java 17 or later
- Gradle (or use the included Gradle Wrapper)

### Steps

1. **Clone the repository** (or extract the provided zip file).
2. **Open a terminal** in the project root directory.
3. **Run the application**:

   ```bash
   ./gradlew bootRun

## Example Requests

### 1. Search available flights
GET http://localhost:9090/api/flights?origin=New%20York&destination=London&date=2026-06-20

### 2. Book a ticket
POST http://localhost:8080/api/bookings
Content-Type: application/json
{
  "flightId": 1,
  "passengerName": "John Doe",
  "seatsBooked": 2
}

### 3. View a booking
GET http://localhost:8080/api/bookings/A3F9K2L1

### 4. Cancel a booking
DELETE http://localhost:8080/api/bookings/A3F9K2L1



## What I would improve with more time
Given the 60‑minute constraint, the current solution focuses on correctness and core functionality. With additional time, I would enhance it as follows:

### Add authentication & authorisation
Use Spring Security with JWT to protect endpoints.
Different roles (e.g., ADMIN can create flights, USER can only book).

### Add integration with a real database
Replace H2 with PostgreSQL or MySQL, and use Flyway/Liquibase for schema migrations.

### Write more tests
Integration tests using TestRestTemplate or WebTestClient.
Contract tests (Spring Cloud Contract) for API versioning.

### Implement a simple flight search cache
Cache search results for popular routes with Caffeine (TTL 1 minute).
