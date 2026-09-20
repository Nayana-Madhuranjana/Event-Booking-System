package com.eventbooking.servlet;

import com.eventbooking.model.*;
import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/submit-review")
public class ReviewServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (currentUser(req) == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        req.setAttribute("events", DataService.events(getServletContext()));
        view(req, resp, "submit-review");
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = currentUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        String id = "R" + System.currentTimeMillis()%100000;
        DataService.addReview(getServletContext(), new Review(id, user.getUserId(), p(req,"eventId"), Integer.parseInt(p(req,"rating")), p(req,"comment"), "Published"));
        resp.sendRedirect(req.getContextPath()+"/ratings-dashboard");
    }
}
