package com.eventbooking.util;

import com.eventbooking.model.*;
import jakarta.servlet.ServletContext;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DataService {
    public static final String USERS = "Users.txt";
    public static final String ADMINS = "Admins.txt";
    public static final String EVENTS = "Events.txt";
    public static final String SEATS = "Seats.txt";
    public static final String BOOKINGS = "BookingLog.txt";
    public static final String REVIEWS = "Reviews.txt";
    public static final String LOGS = "ActivityLogs.txt";

    public static List<User> users(ServletContext c) {
        return FileUtil.readLines(c, USERS).stream().map(User::fromFileString).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<Admin> admins(ServletContext c) {
        return FileUtil.readLines(c, ADMINS).stream().map(Admin::fromFileString).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<Event> events(ServletContext c) {
        return FileUtil.readLines(c, EVENTS).stream().map(Event::fromFileString).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<Seat> seats(ServletContext c) {
        return FileUtil.readLines(c, SEATS).stream().map(Seat::fromFileString).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<Ticket> tickets(ServletContext c) {
        return FileUtil.readLines(c, BOOKINGS).stream().map(Ticket::fromFileString).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<Review> reviews(ServletContext c) {
        return FileUtil.readLines(c, REVIEWS).stream().map(Review::fromFileString).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static Optional<User> findUserByEmailOrId(ServletContext c, String login) {
        return users(c).stream().filter(u -> u.getEmail().equalsIgnoreCase(login) || u.getUserId().equalsIgnoreCase(login)).findFirst();
    }

    public static Optional<Admin> findAdminByEmailOrId(ServletContext c, String login) {
        return admins(c).stream().filter(a -> a.getEmail().equalsIgnoreCase(login) || a.getUserId().equalsIgnoreCase(login)).findFirst();
    }

    public static Optional<Event> findEvent(ServletContext c, String eventId) {
        return events(c).stream().filter(e -> e.getEventId().equalsIgnoreCase(eventId)).findFirst();
    }

    public static Optional<User> findUserById(ServletContext c, String userId) {
        if (userId == null) return Optional.empty();
        return users(c).stream().filter(u -> u.getUserId().equalsIgnoreCase(userId.trim())).findFirst();
    }

    public static void updateUser(ServletContext c, User updatedUser) {
        if (updatedUser == null || updatedUser.getUserId() == null || updatedUser.getUserId().trim().isEmpty()) return;
        List<User> all = users(c);
        List<String> lines = new ArrayList<>();
        lines.add("UserID|FullName|Email|Phone|Password|UserType");
        boolean changed = false;
        for (User u : all) {
            if (u.getUserId().equalsIgnoreCase(updatedUser.getUserId())) {
                lines.add(updatedUser.toFileString());
                changed = true;
            } else {
                lines.add(u.toFileString());
            }
        }
        if (changed) {
            FileUtil.writeLines(c, USERS, lines);
            log(c, "Updated user account: " + updatedUser.getUserId());
        }
    }

    public static void deleteUser(ServletContext c, String userId) {
        if (userId == null || userId.trim().isEmpty()) return;
        String target = userId.trim();
        List<String> userLines = FileUtil.readLines(c, USERS).stream()
                .filter(line -> line.startsWith("UserID|") || !line.startsWith(target + "|"))
                .collect(Collectors.toList());
        FileUtil.writeLines(c, USERS, userLines);

        List<String> bookingLines = FileUtil.readLines(c, BOOKINGS).stream()
                .filter(line -> {
                    String[] parts = line.split("\\|", -1);
                    return line.startsWith("BookingID|") || parts.length < 2 || !parts[1].equalsIgnoreCase(target);
                })
                .collect(Collectors.toList());
        FileUtil.writeLines(c, BOOKINGS, bookingLines);

        List<String> reviewLines = FileUtil.readLines(c, REVIEWS).stream()
                .filter(line -> {
                    String[] parts = line.split("\\|", -1);
                    return line.startsWith("ReviewID|") || parts.length < 2 || !parts[1].equalsIgnoreCase(target);
                })
                .collect(Collectors.toList());
        FileUtil.writeLines(c, REVIEWS, reviewLines);

        log(c, "Deleted user account and related bookings/reviews: " + target);
    }

    public static List<Seat> seatsForEvent(ServletContext c, String eventId) {
        if (eventId == null) return Collections.emptyList();
        return seats(c).stream()
                .filter(s -> s.getEventId().equalsIgnoreCase(eventId))
                .collect(Collectors.toList());
    }

    public static List<Seat> findSeats(ServletContext c, String eventId, List<String> seatNumbers) {
        if (eventId == null || seatNumbers == null || seatNumbers.isEmpty()) return Collections.emptyList();
        List<String> requested = seatNumbers.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(v -> !v.isEmpty())
                .distinct()
                .collect(Collectors.toList());
        return seats(c).stream()
                .filter(s -> s.getEventId().equalsIgnoreCase(eventId))
                .filter(s -> requested.stream().anyMatch(no -> s.getSeatNo().equalsIgnoreCase(no)))
                .collect(Collectors.toList());
    }

    public static void addUser(ServletContext c, User user) {
        FileUtil.appendLine(c, USERS, user.toFileString());
        log(c, "Created user account: " + user.getUserId());
    }

    public static void addAdmin(ServletContext c, Admin admin) {
        FileUtil.appendLine(c, ADMINS, admin.toFileString());
        log(c, "Created admin/organizer account: " + admin.getUserId());
    }

    public static void addEvent(ServletContext c, Event event) {
        FileUtil.appendLine(c, EVENTS, event.toFileString());
        createSeatsForEvent(c, event.getEventId(), event.getTotalSeats());
        log(c, "Created event: " + event.getEventId());
    }

    public static void deleteEvent(ServletContext c, String eventId) {
        List<String> eventLines = FileUtil.readLines(c, EVENTS).stream()
                .filter(line -> line.startsWith("EventID|") || !line.startsWith(eventId + "|"))
                .collect(Collectors.toList());
        FileUtil.writeLines(c, EVENTS, eventLines);
        List<String> seatLines = FileUtil.readLines(c, SEATS).stream()
                .filter(line -> line.startsWith("EventID|") || !line.startsWith(eventId + "|"))
                .collect(Collectors.toList());
        FileUtil.writeLines(c, SEATS, seatLines);
        log(c, "Deleted event and seat data: " + eventId);
    }

    public static void createSeatsForEvent(ServletContext c, String eventId, int totalSeats) {
        List<Seat> existing = seats(c);
        boolean already = existing.stream().anyMatch(s -> s.getEventId().equalsIgnoreCase(eventId));
        if (already) return;
        for (int i = 1; i <= totalSeats; i++) {
            String row = i <= 10 ? "V" : "S";
            String seatNo = row + String.format("%02d", i);
            Seat seat = i <= Math.max(5, totalSeats / 5) ? new VipSeat(eventId, seatNo, "Available") : new StandardSeat(eventId, seatNo, "Available");
            FileUtil.appendLine(c, SEATS, seat.toFileString());
        }
    }

    public static boolean reserveSeat(ServletContext c, String eventId, String seatNo) {
        return reserveSeats(c, eventId, Collections.singletonList(seatNo));
    }

    public static boolean reserveSeats(ServletContext c, String eventId, List<String> seatNumbers) {
        List<String> requestedSeats = seatNumbers == null ? Collections.emptyList() : seatNumbers.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
        if (requestedSeats.isEmpty()) return false;

        List<Seat> all = seats(c);
        for (String seatNo : requestedSeats) {
            boolean available = all.stream().anyMatch(s ->
                    s.getEventId().equalsIgnoreCase(eventId) &&
                    s.getSeatNo().equalsIgnoreCase(seatNo) &&
                    s.isAvailable());
            if (!available) return false;
        }

        for (Seat s : all) {
            if (s.getEventId().equalsIgnoreCase(eventId) &&
                    requestedSeats.stream().anyMatch(seatNo -> s.getSeatNo().equalsIgnoreCase(seatNo))) {
                s.setStatus("Reserved");
            }
        }

        List<String> lines = new ArrayList<>();
        lines.add("EventID|SeatNo|SeatType|Status");
        all.forEach(s -> lines.add(s.toFileString()));
        FileUtil.writeLines(c, SEATS, lines);
        log(c, "Reserved seats " + String.join(",", requestedSeats) + " for event " + eventId);
        return true;
    }

    public static void addTicket(ServletContext c, Ticket t) {
        FileUtil.appendLine(c, BOOKINGS, t.toFileString());
        log(c, "Created booking: " + t.getBookingId());
    }

    public static void updateTicketStatus(ServletContext c, String bookingId, String status) {
        List<Ticket> all = tickets(c);
        List<String> lines = new ArrayList<>();
        lines.add("BookingID|UserID|EventID|SeatNo|TicketType|Status|TotalAmount|BookingDate|QRCode");
        for (Ticket t : all) {
            if (t.getBookingId().equalsIgnoreCase(bookingId)) t.setStatus(status);
            lines.add(t.toFileString());
        }
        FileUtil.writeLines(c, BOOKINGS, lines);
        log(c, "Updated ticket status: " + bookingId + " -> " + status);
    }

    public static void addReview(ServletContext c, Review r) {
        FileUtil.appendLine(c, REVIEWS, r.toFileString());
        log(c, "Created review: " + r.getReviewId());
    }

    public static void deleteReview(ServletContext c, String reviewId) {
        List<String> lines = FileUtil.readLines(c, REVIEWS).stream()
                .filter(line -> line.startsWith("ReviewID|") || !line.startsWith(reviewId + "|"))
                .collect(Collectors.toList());
        FileUtil.writeLines(c, REVIEWS, lines);
        log(c, "Deleted review: " + reviewId);
    }

    public static int availableSeatCount(ServletContext c, String eventId) {
        return (int) seats(c).stream().filter(s -> s.getEventId().equalsIgnoreCase(eventId) && s.isAvailable()).count();
    }

    public static double totalRevenue(ServletContext c) {
        return tickets(c).stream().mapToDouble(Ticket::getTotalAmount).sum();
    }

    public static double averageRating(ServletContext c, String eventId) {
        List<Review> rs = reviews(c).stream().filter(r -> r.getEventId().equalsIgnoreCase(eventId)).collect(Collectors.toList());
        if (rs.isEmpty()) return 0;
        return rs.stream().mapToInt(Review::getRating).average().orElse(0);
    }

    public static List<Event> sortEvents(List<Event> list, String by) {
        List<Event> copy = new ArrayList<>(list);
        quickSort(copy, 0, copy.size() - 1, by == null ? "date" : by);
        return copy;
    }

    private static void quickSort(List<Event> arr, int low, int high, String by) {
        if (low < high) {
            int pi = partition(arr, low, high, by);
            quickSort(arr, low, pi - 1, by);
            quickSort(arr, pi + 1, high, by);
        }
    }

    private static int partition(List<Event> arr, int low, int high, String by) {
        Event pivot = arr.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            boolean before = "popularity".equalsIgnoreCase(by)
                    ? arr.get(j).getPopularity() >= pivot.getPopularity()
                    : arr.get(j).getEventDate().compareTo(pivot.getEventDate()) <= 0;
            if (before) {
                i++;
                Collections.swap(arr, i, j);
            }
        }
        Collections.swap(arr, i + 1, high);
        return i + 1;
    }

    public static void log(ServletContext c, String text) {
        FileUtil.appendLine(c, LOGS, LocalDate.now() + " | " + text);
    }

    public static List<String> logs(ServletContext c) { return FileUtil.readLines(c, LOGS); }
}
