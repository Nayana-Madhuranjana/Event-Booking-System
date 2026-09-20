package com.eventbooking.servlet;

import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/update-ticket")
public class UpdateTicketServlet extends BaseServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        DataService.updateTicketStatus(getServletContext(), p(req,"bookingId"), p(req,"status"));
        resp.sendRedirect(req.getContextPath()+"/admin-dashboard");
    }
}
