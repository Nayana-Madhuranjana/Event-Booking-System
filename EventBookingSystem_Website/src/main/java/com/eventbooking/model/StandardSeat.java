package com.eventbooking.model;

public class StandardSeat extends Seat {
    public StandardSeat(String eventId, String seatNo, String status) {
        super(eventId, seatNo, "Standard", status);
    }
    @Override public double priceMultiplier() { return 1.0; }
    @Override public String displayClass() { return "standard-seat"; }
}
