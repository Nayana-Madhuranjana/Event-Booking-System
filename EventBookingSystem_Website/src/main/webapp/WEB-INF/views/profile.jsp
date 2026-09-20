<%@ include file="header.jsp" %>
<section class="page-head"><span class="eyebrow">Account</span><h1>Profile Management</h1></section>
<section class="section narrow">
    <% if (request.getAttribute("success") != null) { %><div class="alert success"><%=request.getAttribute("success")%></div><% } %>
    <form class="panel" method="post" action="<%=ctx%>/profile">
        <div class="profile-avatar"><%= current.getFullName().substring(0,1).toUpperCase() %></div>
        <div class="form-grid two">
            <label>User ID<input value="<%=current.getUserId()%>" readonly></label>
            <label>Account Type<input value="<%=current.getUserType()%>" readonly></label>
            <label>Full Name<input name="fullName" value="<%=current.getFullName()%>" required></label>
            <label>Email<input value="<%=current.getEmail()%>" readonly></label>
            <label>Phone<input name="phone" value="<%=current.getPhone()%>" required></label>
        </div>
        <button class="btn btn-primary" type="submit">Save Changes</button>
    </form>
</section>
<%@ include file="footer.jsp" %>
