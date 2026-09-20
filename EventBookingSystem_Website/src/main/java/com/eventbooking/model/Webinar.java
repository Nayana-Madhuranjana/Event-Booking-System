package com.eventbooking.model;

public class Webinar extends Event {
    private String onlineLink;
    private String platform;

    public Webinar() {}

    public String getOnlineLink() { return onlineLink; }
    public void setOnlineLink(String onlineLink) { this.onlineLink = onlineLink; }
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    @Override
    public String getDisplayBadge() { return "Online Webinar"; }
}
