package com.eventbooking.model;

public class VipSeat extends Seat {
    public VipSeat(String eventId, String seatNo, String status) {
        super(eventId, seatNo, "VIP", status);
    }
    @Override public double priceMultiplier() { return 1.7; }
    @Override public String displayClass() { return "vip-seat"; }
}
