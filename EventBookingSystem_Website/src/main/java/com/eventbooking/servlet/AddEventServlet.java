package com.eventbooking.servlet;

import com.eventbooking.model.Event;
import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/add-event")
public class AddEventServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        view(req, resp, "add-event");
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        String id = p(req,"eventId").isEmpty() ? "E" + System.currentTimeMillis()%100000 : p(req,"eventId");
        int seats = Integer.parseInt(p(req,"totalSeats"));
        double price = Double.parseDouble(p(req,"ticketPrice"));
        int popularity = p(req,"popularity").isEmpty() ? 50 : Integer.parseInt(p(req,"popularity"));
        Event event = new Event(id, p(req,"eventName"), p(req,"eventType"), p(req,"eventDate"), p(req,"eventTime"), p(req,"venueName"), p(req,"venueAddress"), seats, price, popularity, p(req,"description"), p(req,"organizer"));
        DataService.addEvent(getServletContext(), event);
        resp.sendRedirect(req.getContextPath() + "/events");
    }
}
