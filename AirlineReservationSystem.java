import java.util.*;

// -------------------- Passenger Class --------------------
class Passenger {
    private String passengerId;
    private String name;
    private String email;
    private List<Reservation> reservations = new ArrayList<>();

    public Passenger(String id, String name, String email) {
        this.passengerId = id;
        this.name = name;
        this.email = email;
    }

    public Reservation makeReservation(Flight flight, Seat seat) {
        if (seat.isAvailable()) {
            Reservation reservation = new Reservation(this, flight, seat);
            reservations.add(reservation);
            seat.setAvailable(false);
            return reservation;
        } else {
            System.out.println("Seat " + seat.getSeatNumber() + " is already booked.");
            return null;
        }
    }

    public void viewReservations() {
        System.out.println("\nReservations for " + name + ":");
        for (Reservation res : reservations) {
            System.out.println("Flight: " + res.getFlight().getFlightNumber()
                    + ", Seat: " + res.getSeat().getSeatNumber()
                    + ", Status: " + res.getStatus());
        }
    }

    // Getters
    public String getPassengerId() { return passengerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}

// -------------------- Flight Class --------------------
class Flight {
    private String flightNumber;
    private String destination;
    private List<Seat> seats = new ArrayList<>();

    public Flight(String flightNumber, String destination, int seatCount) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        for (int i = 1; i <= seatCount; i++) {
            seats.add(new Seat("S" + i));
        }
    }

    public Seat getAvailableSeat() {
        for (Seat seat : seats) {
            if (seat.isAvailable()) return seat;
        }
        return null;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    // Getters
    public String getFlightNumber() { return flightNumber; }
    public String getDestination() { return destination; }
}

// -------------------- Seat Class --------------------
class Seat {
    private String seatNumber;
    private boolean available = true;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean status) {
        this.available = status;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}

// -------------------- Reservation Class --------------------
class Reservation {
    private Passenger passenger;
    private Flight flight;
    private Seat seat;
    private String status;

    public Reservation(Passenger passenger, Flight flight, Seat seat) {
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
        this.status = "Confirmed";
    }

    // Getters
    public Passenger getPassenger() { return passenger; }
    public Flight getFlight() { return flight; }
    public Seat getSeat() { return seat; }
    public String getStatus() { return status; }
}

// -------------------- Main Class --------------------
public class AirlineReservationSystem {
    public static void main(String[] args) {
        // Create flights
        Flight flight1 = new Flight("BG001", "Chittagong", 2);
        Flight flight2 = new Flight("BG002", "Cox's Bazar", 1);

        // Create passengers
        Passenger p1 = new Passenger("P001", "Sadia", "sadia@email.com");
        Passenger p2 = new Passenger("P002", "Nafisa", "nafisa@email.com");

        // Make reservations
        Seat seat1 = flight1.getAvailableSeat();
        if (seat1 != null) p1.makeReservation(flight1, seat1);

        Seat seat2 = flight1.getAvailableSeat();
        if (seat2 != null) p2.makeReservation(flight1, seat2);

        // Try booking when full
        Passenger p3 = new Passenger("P003", "Aisha", "aisha@email.com");
        Seat seat3 = flight1.getAvailableSeat();
        if (seat3 != null) {
            p3.makeReservation(flight1, seat3);
        } else {
            System.out.println("\nNo available seats for " + p3.getName() + " on flight " + flight1.getFlightNumber());
        }

        // Show all reservations
        p1.viewReservations();
        p2.viewReservations();
        p3.viewReservations();
    }
}