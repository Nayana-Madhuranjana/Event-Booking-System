package com.eventbooking.servlet;

import com.eventbooking.model.User;
import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/my-tickets")
public class MyTicketsServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = currentUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        req.setAttribute("tickets", DataService.tickets(getServletContext()).stream().filter(t -> t.getUserId().equalsIgnoreCase(user.getUserId())).collect(Collectors.toList()));
        req.setAttribute("events", DataService.events(getServletContext()));
        view(req, resp, "my-tickets");
    }
}
