<%@ page import="java.util.*,com.eventbooking.model.*" %>
<%@ include file="header.jsp" %>
<% List<Ticket> tickets=(List<Ticket>)request.getAttribute("tickets"); List<Event> events=(List<Event>)request.getAttribute("events"); %>
<section class="page-head"><span class="eyebrow">Tickets</span><h1>My Tickets</h1></section>
<section class="section"><div class="ticket-grid">
<% for(Ticket t: tickets){ String eventName=t.getEventId(); String venue=""; for(Event ev: events){ if(ev.getEventId().equals(t.getEventId())){ eventName=ev.getEventName(); venue=ev.getVenueName(); }} %>
    <article class="ticket-card"><div class="ticket-top"><span><%=t.getStatus()%></span><strong><%=t.getBookingId()%></strong></div><h2><%=eventName%></h2><p><%=venue%></p><p>Seats: <strong><%=t.getSeatNo().replace(",", ", ")%></strong> - Type: <%=t.getTicketType()%></p><div class="qr"><%=t.getQrCode()%></div><p>Booked: <%=t.getBookingDate()%> --- Rs. <%=String.format("%,.0f", t.getTotalAmount())%></p><button class="btn btn-outline" onclick="window.print()">Download / Print</button></article>
<% } %>
</div></section>
<%@ include file="footer.jsp" %>
