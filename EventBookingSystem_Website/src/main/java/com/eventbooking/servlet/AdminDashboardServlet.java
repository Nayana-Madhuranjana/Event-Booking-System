package com.eventbooking.servlet;

import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        req.setAttribute("users", DataService.users(getServletContext()));
        req.setAttribute("admins", DataService.admins(getServletContext()));
        req.setAttribute("events", DataService.events(getServletContext()));
        req.setAttribute("tickets", DataService.tickets(getServletContext()));
        req.setAttribute("reviews", DataService.reviews(getServletContext()));
        req.setAttribute("totalRevenue", DataService.totalRevenue(getServletContext()));
        req.setAttribute("logs", DataService.logs(getServletContext()));
        view(req, resp, "admin-dashboard");
    }
}
