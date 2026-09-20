<%@ page import="java.util.*,com.eventbooking.model.*" %>
<%@ include file="header.jsp" %>
<% Event e = (Event) request.getAttribute("event"); Double avg = (Double) request.getAttribute("averageRating"); %>
<% if(e == null){ %>
<section class="page-head"><h1>Event not found</h1><a class="btn btn-primary" href="<%=ctx%>/events">Back to Events</a></section>
<% } else { %>
<section class="details-hero">
    <div><span class="eyebrow"><%=e.getEventType()%></span><h1><%=e.getEventName()%></h1><p><%=e.getDescription()%></p>
        <div class="detail-pills"><span><%=e.getEventDate()%> at <%=e.getEventTime()%></span><span><%=e.getVenueName()%></span><span>⭐ <%=String.format("%.1f", avg)%></span></div>
        <a class="btn btn-primary btn-large" href="<%=ctx%>/seat-map?eventId=<%=e.getEventId()%>">Select Seat</a>
    </div>
</section>
<section class="section grid-two">
    <div class="panel"><h2>Event Information</h2><p><strong>Event ID:</strong> <%=e.getEventId()%></p><p><strong>Venue:</strong> <%=e.getVenueName()%></p><p><strong>Address:</strong> <%=e.getVenueAddress()%></p><p><strong>Organizer:</strong> <%=e.getOrganizer()%></p><p><strong>Base Price:</strong> Rs. <%=String.format("%,.0f", e.getTicketPrice())%></p></div>
    <div class="panel"><h2>Booking Benefits</h2><ul class="premium-list"><li>Instant digital QR ticket</li><li>VIP and Standard seating</li><li>Secure attendee profile</li><li>Post-event review support</li></ul></div>
</section>
<% } %>
<%@ include file="footer.jsp" %>
