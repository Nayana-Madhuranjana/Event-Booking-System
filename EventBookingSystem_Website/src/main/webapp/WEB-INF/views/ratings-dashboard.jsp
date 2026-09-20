<%@ page import="java.util.*,com.eventbooking.model.*" %>
<%@ include file="header.jsp" %>
<% List<Event> events=(List<Event>)request.getAttribute("events"); List<Review> reviews=(List<Review>)request.getAttribute("reviews"); %>
<section class="page-head"><span class="eyebrow">Social Proof</span><h1>Event Ratings Dashboard</h1></section>
<section class="section">
<% for(Event e:events){ int count=0,total=0; for(Review r:reviews){ if(r.getEventId().equals(e.getEventId())){ count++; total+=r.getRating(); }} double avg=count==0?0:(double)total/count; %>
    <div class="panel rating-panel"><div class="rating-head"><h2><%=e.getEventName()%></h2><span>&#11088; <%= String.format("%.1f", avg) %> / 5 (<%= count %> reviews)</span></div>
        <div class="reviews-list"><% for(Review r:reviews){ if(r.getEventId().equals(e.getEventId())){ %><div class="review-item"><strong><%=r.getRating()%> Stars</strong><p><%=r.getComment()%></p><small>By <%=r.getUserId()%>  <%=r.getStatus()%></small><% if(admin){ %><form method="post" action="<%=ctx%>/delete-review"><input type="hidden" name="reviewId" value="<%=r.getReviewId()%>"><br><button class="mini danger">Delete</button></form><% } %></div><% }} %></div>
    </div>
<% } %>
</section>
<%@ include file="footer.jsp" %>
