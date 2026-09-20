package com.eventbooking.model;

public class Attendee extends User {
    public Attendee() {}
    public Attendee(String userId, String fullName, String email, String phone, String password) {
        super(userId, fullName, email, phone, password, "Attendee");
    }

    public String attendeeWelcomeMessage() {
        return "Welcome, " + getFullName() + ". Browse events, reserve seats, and manage your tickets.";
    }
}
