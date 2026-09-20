package com.eventbooking.servlet;

import com.eventbooking.model.Admin;
import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/admin-register")
public class AdminRegisterServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        view(req, resp, "admin-register");
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        String id = p(req,"adminId").isEmpty() ? "A" + System.currentTimeMillis()%100000 : p(req,"adminId");
        DataService.addAdmin(getServletContext(), new Admin(id, p(req,"fullName"), p(req,"email"), p(req,"phone"), p(req,"password"), p(req,"role")));
        resp.sendRedirect(req.getContextPath()+"/admin-dashboard");
    }
}
