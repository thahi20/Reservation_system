<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Reservation" %>
<%@ page import="model.Guest" %>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1"/>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
  <title>View Reservation</title>
</head>
<body>
  <div class="container">
    <div class="header">
      <div>
        <h1>View Reservation</h1>
        <p class="muted">Search by reservation number</p>
      </div>
      <div class="actions">
        <a class="btn ghost" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
        <a class="btn ghost" href="<%=request.getContextPath()%>/logout">Logout</a>
      </div>
    </div>

    <div class="card">
      <h2 class="cardTitle">Search</h2>

      <form method="get" action="<%=request.getContextPath()%>/viewReservation">
        <div class="row">
          <label>Reservation No
            <input name="no" placeholder="R0001" required>
          </label>
          <button type="submit">Search</button>
        </div>
      </form>

      <div class="notice error">
        <%
          Object err = request.getAttribute("error");
          if (err != null) out.print(err.toString());
        %>
      </div>
    </div>

    <%
      Reservation r = (Reservation) request.getAttribute("reservation");
      if (r != null) {
        Guest g = r.getGuest();
    %>
    <div class="card">
      <h2 class="cardTitle">Reservation Details</h2>

      <table class="table">
        <tr><th>Reservation No</th><td><%= r.getReservationNo() %></td></tr>
        <tr><th>Guest Name</th><td><%= g.getName() %></td></tr>
        <tr><th>Address</th><td><%= g.getAddress() %></td></tr>
        <tr><th>Contact</th><td><%= g.getContactNumber() %></td></tr>
        <tr><th>Room Type</th><td><span class="badge"><%= r.getRoomType() %></span></td></tr>
        <tr><th>Check-In</th><td><%= r.getCheckIn() %></td></tr>
        <tr><th>Check-Out</th><td><%= r.getCheckOut() %></td></tr>
        <tr><th>Created At</th><td class="muted"><%= r.getCreatedAt() %></td></tr>
      </table>

      <div class="actions">
        <a class="btn" href="<%=request.getContextPath()%>/bill?no=<%= r.getReservationNo() %>">Generate Bill</a>
      </div>
    </div>
    <%
      }
    %>

    <div class="footer">© Room Reservation System</div>
  </div>
</body>
</html>
