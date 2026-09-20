package com.eventbooking.servlet;

import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/ratings-dashboard")
public class RatingsDashboardServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("events", DataService.events(getServletContext()));
        req.setAttribute("reviews", DataService.reviews(getServletContext()));
        view(req, resp, "ratings-dashboard");
    }
}
