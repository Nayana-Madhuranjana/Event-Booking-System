package com.eventbooking.model;

import java.io.Serializable;

public class Event implements Serializable {
    private String eventId;
    private String eventName;
    private String eventType;
    private String eventDate;
    private String eventTime;
    private String venueName;
    private String venueAddress;
    private int totalSeats;
    private double ticketPrice;
    private int popularity;
    private String description;
    private String organizer;

    public Event() {}

    public Event(String eventId, String eventName, String eventType, String eventDate, String eventTime,
                 String venueName, String venueAddress, int totalSeats, double ticketPrice, int popularity,
                 String description, String organizer) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.totalSeats = totalSeats;
        this.ticketPrice = ticketPrice;
        this.popularity = popularity;
        this.description = description;
        this.organizer = organizer;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
    public String getEventTime() { return eventTime; }
    public void setEventTime(String eventTime) { this.eventTime = eventTime; }
    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }
    public String getVenueAddress() { return venueAddress; }
    public void setVenueAddress(String venueAddress) { this.venueAddress = venueAddress; }
    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }
    public int getPopularity() { return popularity; }
    public void setPopularity(int popularity) { this.popularity = popularity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }

    public String getDisplayBadge() { return eventType; }

    public String toFileString() {
        return String.join("|", safe(eventId), safe(eventName), safe(eventType), safe(eventDate), safe(eventTime),
                safe(venueName), safe(venueAddress), String.valueOf(totalSeats), String.valueOf(ticketPrice),
                String.valueOf(popularity), safe(description), safe(organizer));
    }

    public static Event fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 12 || p[0].equalsIgnoreCase("EventID")) return null;
        return new Event(p[0], p[1], p[2], p[3], p[4], p[5], p[6], parseInt(p[7]), parseDouble(p[8]), parseInt(p[9]), p[10], p[11]);
    }

    protected static int parseInt(String v) { try { return Integer.parseInt(v.trim()); } catch (Exception e) { return 0; } }
    protected static double parseDouble(String v) { try { return Double.parseDouble(v.trim()); } catch (Exception e) { return 0; } }
    protected String safe(String value) { return value == null ? "" : value.replace("|", " ").replace("\n", " ").trim(); }
}
