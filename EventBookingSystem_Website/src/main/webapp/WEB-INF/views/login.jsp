<%@ include file="header.jsp" %>
<section class="auth-wrap">
    <form class="auth-card" method="post" action="<%=ctx%>/login">
        <span class="eyebrow">Secure Access</span>
        <h1>Login</h1>
        <% if (request.getAttribute("error") != null) { %><div class="alert error"><%=request.getAttribute("error")%></div><% } %>
        <% if (request.getAttribute("success") != null) { %><div class="alert success"><%=request.getAttribute("success")%></div><% } %>
        <label>Email or User ID<input type="text" name="login" required></label>
        <label>Password<input type="password" name="password" required></label>
        <button class="btn btn-primary full" type="submit">Login</button>
        <p>New here? <a href="<%=ctx%>/register">Create an account</a></p>
    </form>
</section>
<%@ include file="footer.jsp" %>
