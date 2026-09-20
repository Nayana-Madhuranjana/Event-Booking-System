package com.eventbooking.model;

public class Seminar extends Event {
    private String speakerName;

    public Seminar() {}

    public String getSpeakerName() { return speakerName; }
    public void setSpeakerName(String speakerName) { this.speakerName = speakerName; }

    @Override
    public String getDisplayBadge() { return "Professional Seminar"; }
}
