package com.eventbooking.servlet;

import com.eventbooking.model.Event;
import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/events")
public class EventServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = p(req, "q").toLowerCase();
        String type = p(req, "type");
        String sort = p(req, "sort");
        List<Event> events = DataService.events(getServletContext());
        if (!q.isEmpty()) events = events.stream().filter(e -> e.getEventName().toLowerCase().contains(q) || e.getEventType().toLowerCase().contains(q)).collect(Collectors.toList());
        if (!type.isEmpty()) events = events.stream().filter(e -> e.getEventType().equalsIgnoreCase(type)).collect(Collectors.toList());
        if (!sort.isEmpty()) events = DataService.sortEvents(events, sort);
        req.setAttribute("events", events);
        view(req, resp, "events");
    }
}
