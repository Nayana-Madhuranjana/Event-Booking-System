package com.eventbooking.servlet;

import com.eventbooking.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (currentUser(req) == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        view(req, resp, "profile");
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = currentUser(req);
        if (user != null) {
            user.setFullName(p(req,"fullName"));
            user.setPhone(p(req,"phone"));
            req.getSession().setAttribute("user", user);
            req.setAttribute("success", "Profile updated in session. Update file persistence can be added from this page.");
        }
        view(req, resp, "profile");
    }
}
