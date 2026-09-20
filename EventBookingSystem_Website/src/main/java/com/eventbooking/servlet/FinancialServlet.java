package com.eventbooking.servlet;

import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/financial-summary")
public class FinancialServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        req.setAttribute("tickets", DataService.tickets(getServletContext()));
        req.setAttribute("events", DataService.events(getServletContext()));
        req.setAttribute("totalRevenue", DataService.totalRevenue(getServletContext()));
        view(req, resp, "financial-summary");
    }
}
