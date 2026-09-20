<%@ include file="header.jsp" %>
<section class="page-head"><span class="eyebrow">Admin</span><h1>Event Registration Form</h1></section>
<section class="section narrow">
<form class="panel" method="post" action="<%=ctx%>/add-event">
    <div class="form-grid two">
        <label>Event ID<input name="eventId" placeholder="Auto if empty"></label>
        <label>Event Name<input name="eventName" required></label>
        <label>Event Type<select name="eventType"><option>Concert</option><option>Seminar</option><option>Webinar</option><option>Live Concert</option></select></label>
        <label>Date<input type="date" name="eventDate" required></label>
        <label>Time<input type="time" name="eventTime" required></label>
        <label>Venue Name<input name="venueName" required></label>
        <label>Venue Address<input name="venueAddress" required></label>
        <label>Total Seats<input type="number" name="totalSeats" min="10" value="50" required></label>
        <label>Ticket Price<input type="number" name="ticketPrice" min="0" value="1500" required></label>
        <label>Popularity<input type="number" name="popularity" min="0" max="100" value="50"></label>
        <label>Organizer<input name="organizer" required></label>
    </div>
    <label>Description<textarea name="description" rows="4" required></textarea></label>
    <button class="btn btn-primary" type="submit">Create Event</button>
</form>
</section>
<%@ include file="footer.jsp" %>
