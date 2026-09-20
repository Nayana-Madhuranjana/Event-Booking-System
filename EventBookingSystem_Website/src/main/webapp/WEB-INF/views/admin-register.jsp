<%@ include file="header.jsp" %>
<section class="page-head"><span class="eyebrow">Admin</span><h1>Admin / Organizer Registration</h1></section>
<section class="section narrow"><form class="panel" method="post" action="<%=ctx%>/admin-register"><div class="form-grid two"><label>Admin ID<input name="adminId" placeholder="Auto if empty"></label><label>Full Name<input name="fullName" required></label><label>Email<input type="email" name="email" required></label><label>Phone<input name="phone" required></label><label>Role<select name="role"><option>Admin</option><option>Organizer</option></select></label><label>Password<input type="password" name="password" required></label></div><button class="btn btn-primary">Register Account</button></form></section>
<%@ include file="footer.jsp" %>
