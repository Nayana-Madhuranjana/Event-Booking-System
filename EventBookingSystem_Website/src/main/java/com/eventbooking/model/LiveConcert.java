package com.eventbooking.model;

public class LiveConcert extends Event {
    private String artistName;
    private String concertDuration;

    public LiveConcert() {}

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }
    public String getConcertDuration() { return concertDuration; }
    public void setConcertDuration(String concertDuration) { this.concertDuration = concertDuration; }

    @Override
    public String getDisplayBadge() { return "Live Concert"; }
}
