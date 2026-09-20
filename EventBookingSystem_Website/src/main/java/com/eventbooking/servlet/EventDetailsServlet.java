package com.eventbooking.servlet;

import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/event-details")
public class EventDetailsServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = p(req,"id");
        req.setAttribute("event", DataService.findEvent(getServletContext(), id).orElse(null));
        req.setAttribute("reviews", DataService.reviews(getServletContext()));
        req.setAttribute("averageRating", DataService.averageRating(getServletContext(), id));
        view(req, resp, "event-details");
    }
}
