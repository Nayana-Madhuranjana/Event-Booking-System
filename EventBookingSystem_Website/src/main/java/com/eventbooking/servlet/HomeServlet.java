package com.eventbooking.servlet;

import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet({"", "/home"})
public class HomeServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/admin-dashboard");
            return;
        }
        if (currentUser(req) != null) {
            resp.sendRedirect(req.getContextPath() + "/events");
            return;
        }
        req.setAttribute("events", DataService.events(getServletContext()));
        req.setAttribute("totalRevenue", DataService.totalRevenue(getServletContext()));
        view(req, resp, "home");
    }
}
