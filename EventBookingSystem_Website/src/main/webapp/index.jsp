<%
    if (session.getAttribute("admin") != null) {
        response.sendRedirect(request.getContextPath() + "/admin-dashboard");
    } else if (session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/events");
    } else {
        response.sendRedirect(request.getContextPath() + "/home");
    }
%>
