<%@ include file="header.jsp" %>
<section class="auth-wrap wide">
    <form class="auth-card" method="post" action="<%=ctx%>/register" onsubmit="return validatePasswords()">
        <span class="eyebrow">Join EventPro</span>
        <h1>Create Account</h1>
        <div class="form-grid two">
            <label>Full Name<input type="text" name="fullName" required></label>
            <label>Email<input type="email" name="email" required></label>
            <label>Phone<input type="text" name="phone" required></label>
            <label>Password<input id="password" type="password" name="password" required></label>
            <label>Confirm Password<input id="confirmPassword" type="password" required></label>
        </div>
        <button class="btn btn-primary full" type="submit">Register</button>
    </form>
</section>
<%@ include file="footer.jsp" %>
