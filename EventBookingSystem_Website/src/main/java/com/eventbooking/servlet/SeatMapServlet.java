package com.eventbooking.servlet;

import com.eventbooking.model.Seat;
import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/seat-map")
public class SeatMapServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventId = p(req,"eventId");
        req.setAttribute("event", DataService.findEvent(getServletContext(), eventId).orElse(null));
        req.setAttribute("seats", DataService.seats(getServletContext()).stream().filter(s -> s.getEventId().equalsIgnoreCase(eventId)).collect(Collectors.toList()));
        view(req, resp, "seat-map");
    }
}
