package com.example.project1;

import java.time.LocalDate;


public class Passenger implements Comparable<Passenger> {

    private int flightNum;
    private int ticketNum;
    private String name;
    private String passport;
    private String nationality;
    private LocalDate birthDate;

    public Passenger(int flightNum, int ticketNum, String name, String passport, String nationality, LocalDate birthDate){
        this.flightNum = flightNum;
        this.ticketNum = ticketNum;
        this.name = name;
        this.passport = passport;
        this.nationality = nationality;
        this.birthDate = birthDate;
    }

    public int getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(int flightNum) {
        this.flightNum = flightNum;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    public int compareTo(Passenger p){
        return this.getName().compareTo(p.getName());
    }

    public boolean equals(Passenger p){
        return this.getPassport().equals(p.getPassport());
    }

    public String toString() {
        return "Flight Number: " + this.getFlightNum() + "\n" +
                "Ticket Number: " + this.getTicketNum() + "\n" +
                "Name: " + this.getName() + "\n" +
                "Passport: " + this.getPassport() + "\n" +
                "Nationality :" + this.getNationality() + "\n" +
                "Birth Date :" + this.getBirthDate().toString() + "\n";
    }


}
