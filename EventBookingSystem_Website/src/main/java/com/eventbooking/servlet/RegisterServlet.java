package com.eventbooking.servlet;

import com.eventbooking.model.*;
import com.eventbooking.util.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { view(req, resp, "register"); }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String type = p(req, "userType");
        String id = ("Admin".equalsIgnoreCase(type) ? "A" : "U") + System.currentTimeMillis()%100000;
        if ("Admin".equalsIgnoreCase(type)) {
            DataService.addAdmin(getServletContext(), new Admin(id, p(req,"fullName"), p(req,"email"), p(req,"phone"), p(req,"password"), "Admin"));
        } else {
            DataService.addUser(getServletContext(), new Attendee(id, p(req,"fullName"), p(req,"email"), p(req,"phone"), p(req,"password")));
        }
        req.setAttribute("success", "Account created successfully. Please login.");
        view(req, resp, "login");
    }
}
