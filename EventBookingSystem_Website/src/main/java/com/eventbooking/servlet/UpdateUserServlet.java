package com.eventbooking.servlet;

import com.eventbooking.model.User;
import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/update-user")
public class UpdateUserServlet extends BaseServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }

        String userId = p(req, "userId");
        String fullName = p(req, "fullName");
        String email = p(req, "email");
        String phone = p(req, "phone");
        String password = p(req, "password");
        String userType = p(req, "userType");

        if (!userId.isEmpty() && !fullName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if (userType.isEmpty()) userType = "Attendee";
            DataService.updateUser(getServletContext(), new User(userId, fullName, email, phone, password, userType));
        }
        resp.sendRedirect(req.getContextPath()+"/admin-dashboard");
    }
}
