package com.eventbooking.servlet;

import com.eventbooking.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {
    protected void view(HttpServletRequest req, HttpServletResponse resp, String page) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + page + ".jsp").forward(req, resp);
    }

    protected User currentUser(HttpServletRequest req) {
        Object user = req.getSession().getAttribute("user");
        return user instanceof User ? (User) user : null;
    }

    protected boolean isAdmin(HttpServletRequest req) {
        Object admin = req.getSession().getAttribute("admin");
        return admin != null;
    }

    protected void requireLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (currentUser(req) == null && !isAdmin(req)) resp.sendRedirect(req.getContextPath() + "/login");
    }

    protected String p(HttpServletRequest req, String name) {
        String value = req.getParameter(name);
        return value == null ? "" : value.trim();
    }
}
