package com.example.project1;



public class Flight implements Comparable<Flight> {

    private int flightNumber;
    private String name;
    private String from;
    private String to;
    private int capacity;
    LinkedList<Passenger> passengers = new LinkedList<>();

    public Flight(int flightNumber, String name, String from, String to, int capacity) {
        this.flightNumber = flightNumber;
        this.name = name;
        this.from = from;
        this.to = to;
        this.capacity = capacity;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public String getName() {
        return name;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getCapacity() {
        return capacity;
    }

    public LinkedList<Passenger> getPassengers() {
        return passengers;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }




    public String toString() {
        return "\n------------------------------------------------------\n" +
                "Flight Number: " + flightNumber + "\n" +
                "Flight Name: " + name + "\n" +
                "From: " + from + "\n" +
                "To: " + to + "\n" +
                "Capacity: " + capacity + "\n"+
                "Passengers: " + passengers + "\n"
                + "\n------------------------------------------------------\n";

    }


    @Override
    public int compareTo(Flight o) {
        return Integer.compare(this.flightNumber, o.flightNumber);
    }

    public int getAvailableSeats() {
        return capacity - passengers.size();
    }

}
