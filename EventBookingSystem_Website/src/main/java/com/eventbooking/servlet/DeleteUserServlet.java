package com.eventbooking.servlet;

import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/delete-user")
public class DeleteUserServlet extends BaseServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        DataService.deleteUser(getServletContext(), p(req,"userId"));
        resp.sendRedirect(req.getContextPath()+"/admin-dashboard");
    }
}
