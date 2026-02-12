<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1"/>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
  <title>Login</title>
</head>
<body>
  <div class="container">
    <div class="header">
      <div>
        <h1>Room Reservation</h1>
        <p class="muted">Login to continue</p>
      </div>
    </div>

    <div class="card">
      <h2 class="cardTitle">Login</h2>

      <form method="post" action="<%=request.getContextPath()%>/login">
        <div class="grid2">
          <label>Username
            <input name="username" placeholder="admin" required>
          </label>
          <label>Password
            <input name="password" type="password" placeholder="admin123" required>
          </label>
        </div>

        <div class="actions">
          <button type="submit">Sign in</button>
        </div>

        <div class="notice error">
          <%
            Object err = request.getAttribute("error");
            if (err != null) out.print(err.toString());
          %>
        </div>
      </form>

      <p class="muted small">Tip: default user is <b>admin</b> / <b>admin123</b></p>
    </div>

    <div class="footer">© Room Reservation System</div>
  </div>
</body>
</html>
