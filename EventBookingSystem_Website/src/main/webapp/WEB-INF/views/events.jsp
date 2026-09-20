<%@ page import="java.util.*,com.eventbooking.model.Event,com.eventbooking.util.DataService" %>
<%@ include file="header.jsp" %>
<% List<Event> events = (List<Event>) request.getAttribute("events"); %>
<section class="page-head"><span class="eyebrow">Discover</span><h1>Event Search & Discovery</h1></section>
<section class="section">
    <form class="filter-bar" method="get" action="<%=ctx%>/events">
        <input name="q" placeholder="Search event name or category">
        <select name="type"><option value="">All Types</option><option>Concert</option><option>Seminar</option><option>Webinar</option><option>Live Concert</option></select>
        <select name="sort"><option value="">Sort</option><option value="date">Date</option><option value="popularity">Popularity</option></select>
        <button class="btn btn-primary" type="submit">Search</button>
    </form>
    <div class="event-grid">
        <% for(Event e: events){ %>
        <article class="event-card premium-card">
            <div class="event-img"><span><%=e.getEventType()%></span></div>
            <div class="event-body">
                <h3><%=e.getEventName()%></h3>
                <p><%=e.getDescription()%></p>
                <div class="event-meta"><span>&#128197; <%= e.getEventDate() %> <%= e.getEventTime() %></span><span>&#128205; <%= e.getVenueName() %></span></div>
                <div class="price-row"><strong>Rs. <%=String.format("%,.0f", e.getTicketPrice())%></strong><small><%=DataService.availableSeatCount(application, e.getEventId())%> seats left</small></div>
                <div class="card-actions"><a class="btn btn-outline" href="<%=ctx%>/event-details?id=<%=e.getEventId()%>">Details</a><a class="btn btn-primary" href="<%=ctx%>/seat-map?eventId=<%=e.getEventId()%>">Book Now</a></div>
            </div>
        </article>
        <% } %>
    </div>
</section>
<%@ include file="footer.jsp" %>
