package com.eventbooking.model;

public class Ticket {
    private String bookingId;
    private String userId;
    private String eventId;
    private String seatNo;
    private String ticketType;
    private String status;
    private double totalAmount;
    private String bookingDate;
    private String qrCode;

    public Ticket() {}

    public Ticket(String bookingId, String userId, String eventId, String seatNo, String ticketType, String status,
                  double totalAmount, String bookingDate, String qrCode) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.seatNo = seatNo;
        this.ticketType = ticketType;
        this.status = status;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
        this.qrCode = qrCode;
    }

    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getEventId() { return eventId; }
    public String getSeatNo() { return seatNo; }
    public String getTicketType() { return ticketType; }
    public String getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }
    public String getBookingDate() { return bookingDate; }
    public String getQrCode() { return qrCode; }
    public void setStatus(String status) { this.status = status; }

    public static double calculatePrice(double basePrice, String ticketType, int quantity) {
        double multiplier = "VIP".equalsIgnoreCase(ticketType) ? 1.7 : 1.0;
        double subtotal = basePrice * multiplier * quantity;
        return quantity >= 3 ? subtotal * 0.9 : subtotal;
    }

    public static double calculatePrice(double basePrice, java.util.List<? extends Seat> seats) {
        if (seats == null || seats.isEmpty()) return 0;
        double subtotal = 0;
        for (Seat seat : seats) {
            if (seat != null) subtotal += basePrice * seat.priceMultiplier();
        }
        return seats.size() >= 3 ? subtotal * 0.9 : subtotal;
    }

    public String toFileString() {
        return String.join("|", bookingId, userId, eventId, seatNo, ticketType, status, String.valueOf(totalAmount), bookingDate, qrCode);
    }

    public static Ticket fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 9 || p[0].equalsIgnoreCase("BookingID")) return null;
        double amount;
        try { amount = Double.parseDouble(p[6]); } catch (Exception e) { amount = 0; }
        return new Ticket(p[0], p[1], p[2], p[3], p[4], p[5], amount, p[7], p[8]);
    }
}
