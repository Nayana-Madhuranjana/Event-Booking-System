package com.eventbooking.servlet;

import com.eventbooking.model.*;
import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { view(req, resp, "login"); }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = p(req, "login");
        String password = p(req, "password");
        Optional<Admin> admin = DataService.findAdminByEmailOrId(getServletContext(), login);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            req.getSession().setAttribute("admin", admin.get());
            req.getSession().setAttribute("user", admin.get());
            resp.sendRedirect(req.getContextPath() + "/admin-dashboard");
            return;
        }
        Optional<User> user = DataService.findUserByEmailOrId(getServletContext(), login);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            req.getSession().setAttribute("user", user.get());
            resp.sendRedirect(req.getContextPath() + "/events");
            return;
        }
        req.setAttribute("error", "Invalid email/user ID or password.");
        view(req, resp, "login");
    }
}
