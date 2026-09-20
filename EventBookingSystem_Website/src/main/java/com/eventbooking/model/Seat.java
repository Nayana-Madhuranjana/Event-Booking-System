package com.eventbooking.model;

public abstract class Seat {
    private String eventId;
    private String seatNo;
    private String seatType;
    private String status;

    public Seat(String eventId, String seatNo, String seatType, String status) {
        this.eventId = eventId;
        this.seatNo = seatNo;
        this.seatType = seatType;
        this.status = status;
    }

    public String getEventId() { return eventId; }
    public String getSeatNo() { return seatNo; }
    public String getSeatType() { return seatType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isAvailable() { return "Available".equalsIgnoreCase(status); }
    public abstract double priceMultiplier();
    public abstract String displayClass();

    public String toFileString() {
        return String.join("|", eventId, seatNo, seatType, status);
    }

    public static Seat fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 4 || p[0].equalsIgnoreCase("EventID")) return null;
        if ("VIP".equalsIgnoreCase(p[2])) return new VipSeat(p[0], p[1], p[3]);
        return new StandardSeat(p[0], p[1], p[3]);
    }
}
