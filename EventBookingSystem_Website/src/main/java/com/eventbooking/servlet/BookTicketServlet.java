package com.eventbooking.servlet;

import com.eventbooking.model.*;
import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/book-ticket")
public class BookTicketServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (currentUser(req) == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        String eventId = p(req,"eventId");
        String seatNo = p(req,"seatNo");
        List<String> selectedSeats = parseSeatNumbers(seatNo);
        Optional<Event> event = DataService.findEvent(getServletContext(), eventId);
        req.setAttribute("event", event.orElse(null));
        prepareSeatSummary(req, eventId, selectedSeats, event.orElse(null));
        view(req, resp, "book-ticket");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = currentUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }

        String eventId = p(req,"eventId");
        List<String> selectedSeats = parseSeatNumbers(p(req,"seatNo"));
        Optional<Event> event = DataService.findEvent(getServletContext(), eventId);

        if (event.isEmpty()) { resp.sendRedirect(req.getContextPath()+"/events"); return; }
        if (selectedSeats.isEmpty()) {
            req.setAttribute("error", "Please select at least one seat from the seat map.");
            req.setAttribute("event", event.get());
            prepareSeatSummary(req, eventId, selectedSeats, event.get());
            view(req, resp, "book-ticket");
            return;
        }

        List<Seat> selectedSeatObjects = DataService.findSeats(getServletContext(), eventId, selectedSeats);
        if (selectedSeatObjects.size() != selectedSeats.size()) {
            req.setAttribute("error", "Some selected seats could not be found. Please choose seats again.");
            req.setAttribute("event", event.get());
            prepareSeatSummary(req, eventId, selectedSeats, event.get());
            view(req, resp, "book-ticket");
            return;
        }

        boolean reserved = DataService.reserveSeats(getServletContext(), eventId, selectedSeats);
        if (!reserved) {
            req.setAttribute("error", "One or more selected seats are already reserved. Please choose available seats again.");
            req.setAttribute("event", event.get());
            prepareSeatSummary(req, eventId, selectedSeats, event.get());
            view(req, resp, "book-ticket");
            return;
        }

        double amount = Ticket.calculatePrice(event.get().getTicketPrice(), selectedSeatObjects);
        String typeSummary = buildTypeSummary(selectedSeatObjects);
        String bookingId = "B" + System.currentTimeMillis()%100000;
        String seatList = String.join(",", selectedSeats);
        String qr = "QR-" + bookingId + "-" + user.getUserId() + "-" + eventId + "-" + seatList.replace(",", "_");
        Ticket ticket = new Ticket(bookingId, user.getUserId(), eventId, seatList, typeSummary, "Active", amount, LocalDate.now().toString(), qr);
        DataService.addTicket(getServletContext(), ticket);
        resp.sendRedirect(req.getContextPath()+"/my-tickets");
    }

    private void prepareSeatSummary(HttpServletRequest req, String eventId, List<String> selectedSeats, Event event) {
        req.setAttribute("seatNo", String.join(",", selectedSeats));
        req.setAttribute("seatCount", selectedSeats.size());

        List<Seat> seatObjects = DataService.findSeats(getServletContext(), eventId, selectedSeats);
        long vipCount = seatObjects.stream().filter(s -> "VIP".equalsIgnoreCase(s.getSeatType())).count();
        long standardCount = seatObjects.stream().filter(s -> "Standard".equalsIgnoreCase(s.getSeatType())).count();
        double totalAmount = event == null ? 0 : Ticket.calculatePrice(event.getTicketPrice(), seatObjects);

        req.setAttribute("vipCount", (int) vipCount);
        req.setAttribute("standardCount", (int) standardCount);
        req.setAttribute("ticketTypeSummary", buildTypeSummary(seatObjects));
        req.setAttribute("calculatedTotal", totalAmount);
    }

    private String buildTypeSummary(List<Seat> seats) {
        if (seats == null || seats.isEmpty()) return "Not selected";
        long vip = seats.stream().filter(s -> "VIP".equalsIgnoreCase(s.getSeatType())).count();
        long standard = seats.stream().filter(s -> "Standard".equalsIgnoreCase(s.getSeatType())).count();
        if (vip > 0 && standard > 0) return "Mixed (VIP x" + vip + ", Standard x" + standard + ")";
        if (vip > 0) return "VIP";
        return "Standard";
    }

    private List<String> parseSeatNumbers(String value) {
        if (value == null || value.trim().isEmpty()) return Collections.emptyList();
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
}
