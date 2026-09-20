<%@ page import="java.util.*,com.eventbooking.model.*" %>
<%@ include file="header.jsp" %>
<% List<Ticket> tickets=(List<Ticket>)request.getAttribute("tickets"); List<Event> events=(List<Event>)request.getAttribute("events"); double totalRevenue=(Double)request.getAttribute("totalRevenue"); double vip=0, standard=0, mixed=0; for(Ticket t:tickets){ if("VIP".equalsIgnoreCase(t.getTicketType())) vip+=t.getTotalAmount(); else if("Standard".equalsIgnoreCase(t.getTicketType())) standard+=t.getTotalAmount(); else mixed+=t.getTotalAmount(); } %>
<section class="page-head"><span class="eyebrow">Finance</span><h1>Financial Summary Panel</h1></section>
<section class="section">
    <div class="stats-grid"><div class="stat"><span>Rs. <%=String.format("%,.0f", totalRevenue)%></span><p>Total Revenue</p></div><div class="stat"><span><%=tickets.size()%></span><p>Total Ticket Sales</p></div><div class="stat"><span>Rs. <%=String.format("%,.0f", vip)%></span><p>VIP Revenue</p></div><div class="stat"><span>Rs. <%=String.format("%,.0f", standard)%></span><p>Standard Revenue</p></div><div class="stat"><span>Rs. <%=String.format("%,.0f", mixed)%></span><p>Mixed Seat Revenue</p></div></div>
    <div class="panel"><h2>Revenue by Event</h2><div class="table-wrap"><table><tr><th>Event</th><th>Tickets Sold</th><th>Revenue</th></tr><% for(Event e: events){ int count=0; double rev=0; for(Ticket t:tickets){ if(t.getEventId().equals(e.getEventId())){ count++; rev+=t.getTotalAmount(); }} %><tr><td><%=e.getEventName()%></td><td><%=count%></td><td>Rs. <%=String.format("%,.0f", rev)%></td></tr><% } %></table></div></div>
    <button class="btn btn-outline" onclick="window.print()">Export Report</button>
</section>
<%@ include file="footer.jsp" %>
