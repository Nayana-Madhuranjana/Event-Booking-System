package com.eventbooking.servlet;

import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/delete-event")
public class DeleteEventServlet extends BaseServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        DataService.deleteEvent(getServletContext(), p(req,"eventId"));
        resp.sendRedirect(req.getContextPath()+"/admin-dashboard");
    }
}
