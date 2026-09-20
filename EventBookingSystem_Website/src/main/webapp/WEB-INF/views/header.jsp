<%@ page import="com.eventbooking.model.User,com.eventbooking.model.Admin" %>
<%
    String ctx = request.getContextPath();
    User current = (User) session.getAttribute("user");
    Admin adminUser = (Admin) session.getAttribute("admin");
    boolean admin = adminUser != null;
    String brandHref = current == null ? ctx + "/home" : (admin ? ctx + "/admin-dashboard" : ctx + "/events");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EventPro Booking</title>
    <link rel="stylesheet" href="<%=ctx%>/assets/css/style.css">
    <script defer src="<%=ctx%>/assets/js/main.js?v=4"></script>
</head>
<body>
<header class="topbar">
    <a class="brand" href="<%=brandHref%>"><span class="brand-icon">E</span><span>EventPro</span></a>
    <button class="menu-btn" onclick="toggleMenu()">☰</button>
    <nav id="mainNav" class="nav">
        <% if (current == null) { %><a href="<%=ctx%>/home">Home</a><% } %>
        <a href="<%=ctx%>/events">Events</a>
        <a href="<%=ctx%>/ratings-dashboard">Ratings</a>
        <% if (current != null) { %>
            <a href="<%=ctx%>/my-tickets">My Tickets</a>
            <a href="<%=ctx%>/submit-review">Review</a>
            <a href="<%=ctx%>/profile">Profile</a>
        <% } %>
        <% if (admin) { %>
            <a href="<%=ctx%>/admin-dashboard">Admin</a>
            <a href="<%=ctx%>/financial-summary">Finance</a>
            <a href="<%=ctx%>/add-event">Add Event</a>
        <% } %>
        <% if (current == null) { %>
            <a class="btn btn-ghost" href="<%=ctx%>/login">Login</a>
            <a class="btn btn-primary" href="<%=ctx%>/register">Register</a>
        <% } else { %>
            <a class="btn btn-primary" href="<%=ctx%>/logout">Logout</a>
        <% } %>
    </nav>
</header>
<main>
