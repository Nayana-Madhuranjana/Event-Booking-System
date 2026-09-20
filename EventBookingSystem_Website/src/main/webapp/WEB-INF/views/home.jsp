<%@ page import="java.util.*,com.eventbooking.model.Event,com.eventbooking.util.DataService" %>
<%@ include file="header.jsp" %>
<%
    List<Event> events = (List<Event>) request.getAttribute("events");
    double totalRevenue = request.getAttribute("totalRevenue") == null ? 0 : (Double) request.getAttribute("totalRevenue");
%>
<section class="hero">
    <div class="hero-content">
        <span class="eyebrow">Premium Event Management Platform</span>
        <h1>Book seats, manage tickets, and run events with confidence.</h1>
        <p>Discover concerts, seminars, webinars, and live experiences. Reserve seats, generate QR tickets, track revenue, and collect attendee reviews.</p>
        <div class="hero-actions">
            <a class="btn btn-primary btn-large" href="<%=ctx%>/events">Browse Events</a>
            <a class="btn btn-outline btn-large" href="<%=ctx%>/register">Create Account</a>
        </div>
    </div>
    <div class="hero-card glass">
        <div class="metric"><span><%= events.size() %></span><small>Active Events</small></div>
        <div class="metric"><span>Rs. <%= String.format("%,.0f", totalRevenue) %></span><small>Total Sales</small></div>
        <div class="metric"><span>QR</span><small>Digital Tickets</small></div>
    </div>
</section>

<section class="section soft">
    <div class="section-title"><span>Featured</span><h2>Upcoming Events</h2></div>
    <div class="event-grid">
        <% for (Event e : events) { %>
            <article class="event-card">
                <div class="event-img"><span><%= e.getEventType() %></span></div>
                <div class="event-body">
                    <h3><%= e.getEventName() %></h3>
                    <p><%= e.getDescription() %></p>
                    <div class="event-meta"><span><%= e.getEventDate() %></span><span><%= e.getVenueName() %></span></div>
                    <a class="btn btn-primary full" href="<%=ctx%>/event-details?id=<%=e.getEventId()%>">View Details</a>
                </div>
            </article>
        <% } %>
    </div>
</section>
<%@ include file="footer.jsp" %>
