package com.eventbooking.model;

public class Review {
    private String reviewId;
    private String userId;
    private String eventId;
    private int rating;
    private String comment;
    private String status;

    public Review() {}

    public Review(String reviewId, String userId, String eventId, int rating, String comment, String status) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.eventId = eventId;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
    }

    public String getReviewId() { return reviewId; }
    public String getUserId() { return userId; }
    public String getEventId() { return eventId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public String getStatus() { return status; }

    public String toFileString() {
        return String.join("|", reviewId, userId, eventId, String.valueOf(rating), comment == null ? "" : comment.replace("|", " "), status);
    }

    public static Review fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 6 || p[0].equalsIgnoreCase("ReviewID")) return null;
        int rating;
        try { rating = Integer.parseInt(p[3]); } catch (Exception e) { rating = 0; }
        return new Review(p[0], p[1], p[2], rating, p[4], p[5]);
    }
}
