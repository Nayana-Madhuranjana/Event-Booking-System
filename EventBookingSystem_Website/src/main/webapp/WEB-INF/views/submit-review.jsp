<%@ page import="java.util.*,com.eventbooking.model.Event" %>
<%@ include file="header.jsp" %>
<% List<Event> events=(List<Event>)request.getAttribute("events"); %>
<section class="page-head"><span class="eyebrow">Feedback</span><h1>Review Submission Page</h1></section>
<section class="section narrow"><form class="panel" method="post" action="<%=ctx%>/submit-review"><label>Event<select name="eventId"><% for(Event e:events){ %><option value="<%=e.getEventId()%>"><%=e.getEventName()%></option><% } %></select></label><label>Rating<select name="rating"><option value="5">5 - Excellent</option><option value="4">4 - Very Good</option><option value="3">3 - Good</option><option value="2">2 - Fair</option><option value="1">1 - Poor</option></select></label><label>Comment<textarea name="comment" rows="5" required></textarea></label><button class="btn btn-primary">Submit Review</button></form></section>
<%@ include file="footer.jsp" %>
