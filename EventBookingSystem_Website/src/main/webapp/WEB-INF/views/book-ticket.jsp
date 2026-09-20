<%@ page import="com.eventbooking.model.Event" %>
<%@ include file="header.jsp" %>
<%
    Event e = (Event) request.getAttribute("event");
    String seatNo = (String) request.getAttribute("seatNo");
    Integer seatCountObj = (Integer) request.getAttribute("seatCount");
    Integer vipCountObj = (Integer) request.getAttribute("vipCount");
    Integer standardCountObj = (Integer) request.getAttribute("standardCount");
    Double calculatedTotalObj = (Double) request.getAttribute("calculatedTotal");
    String ticketTypeSummary = (String) request.getAttribute("ticketTypeSummary");
    int seatCount = seatCountObj == null ? (seatNo == null || seatNo.trim().isEmpty() ? 0 : seatNo.split(",").length) : seatCountObj;
    int vipCount = vipCountObj == null ? 0 : vipCountObj;
    int standardCount = standardCountObj == null ? 0 : standardCountObj;
    double calculatedTotal = calculatedTotalObj == null ? 0 : calculatedTotalObj;
%>
<section class="page-head"><span class="eyebrow">Ticketing</span><h1>Ticket Purchase Form</h1><p>Confirm your selected seats and generate your QR ticket.</p></section>
<section class="section narrow">
<% if (e != null) { %>
<form class="panel" method="post" action="<%=ctx%>/book-ticket">
    <% if (request.getAttribute("error") != null) { %><div class="alert error"><%=request.getAttribute("error")%></div><% } %>
    <input type="hidden" name="eventId" value="<%=e.getEventId()%>">
    <input type="hidden" name="seatNo" value="<%=seatNo == null ? "" : seatNo%>">
    <input type="hidden" id="quantity" name="quantity" value="<%=seatCount%>">
    <div class="ticket-preview">
        <h2><%=e.getEventName()%></h2>
        <p>Seats <strong><%=seatNo == null || seatNo.isEmpty() ? "Not selected" : seatNo.replace(",", ", ")%></strong> - <%=e.getVenueName()%></p>
        <p>Total selected seats: <strong><%=seatCount%></strong></p>
        <p>Seat types: <strong><%=ticketTypeSummary == null ? "Not selected" : ticketTypeSummary%></strong></p>
        <p>VIP seats: <strong><%=vipCount%></strong> | Standard seats: <strong><%=standardCount%></strong></p>
        <% if (seatCount >= 3) { %><p class="hint">10% group discount applied because you selected 3 or more seats.</p><% } %>
    </div>
    <div class="form-grid two">
        <label>Attendee Name<input value="<%=current.getFullName()%>" readonly></label>
        <label>User ID<input value="<%=current.getUserId()%>" readonly></label>
        <label>Payment Method<select name="payment"><option>Cash</option><option>Card</option><option>Online Transfer</option></select></label>
        <label>Total Amount<input id="totalAmount" value="Rs. <%=String.format("%,.0f", calculatedTotal)%>" readonly></label>
    </div>
    <% if (seatCount > 0) { %>
        <button class="btn btn-primary" type="submit">Confirm Booking</button>
    <% } else { %>
        <a class="btn btn-primary" href="<%=ctx%>/seat-map?eventId=<%=e.getEventId()%>">Select Seats</a>
    <% } %>
</form>
<% } else { %>
    <div class="panel"><div class="alert error">Event not found.</div><a class="btn btn-primary" href="<%=ctx%>/events">Back to Events</a></div>
<% } %>
</section>
<%@ include file="footer.jsp" %>
