package com.eventbooking.servlet;

import com.eventbooking.util.DataService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/delete-review")
public class DeleteReviewServlet extends BaseServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        DataService.deleteReview(getServletContext(), p(req,"reviewId"));
        resp.sendRedirect(req.getContextPath()+"/ratings-dashboard");
    }
}
