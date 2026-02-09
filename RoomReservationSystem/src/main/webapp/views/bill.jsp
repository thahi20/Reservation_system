<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Bill" %>
<%@ page import="model.Reservation" %>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1"/>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
  <title>Bill</title>
</head>
<body>
  <div class="container">
    <div class="header">
      <div>
        <h1>Bill</h1>
        <p class="muted">Generate bill using reservation number</p>
      </div>
      <div class="actions">
        <a class="btn ghost" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
        <a class="btn ghost" href="<%=request.getContextPath()%>/logout">Logout</a>
      </div>
    </div>

    <div class="card">
      <h2 class="cardTitle">Generate Bill</h2>

      <form method="get" action="<%=request.getContextPath()%>/bill">
        <div class="row">
          <label>Reservation No
            <input name="no" placeholder="R0001" required>
          </label>
          <button type="submit">Generate</button>
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
      Bill bill = (Bill) request.getAttribute("bill");
      Reservation r = (Reservation) request.getAttribute("reservation");
      if (bill != null && r != null) {
    %>
    <div class="card">
      <h2 class="cardTitle">Bill Summary</h2>

      <table class="table">
        <tr><th>Reservation No</th><td><%= bill.getReservationNo() %></td></tr>
        <tr><th>Guest Name</th><td><%= bill.getGuestName() %></td></tr>
        <tr><th>Room Type</th><td><span class="badge"><%= bill.getRoomType() %></span></td></tr>
        <tr><th>Check-In</th><td><%= r.getCheckIn() %></td></tr>
        <tr><th>Check-Out</th><td><%= r.getCheckOut() %></td></tr>
        <tr><th>Nights</th><td><%= bill.getNights() %></td></tr>
        <tr><th>Rate / Night</th><td><%= bill.getRatePerNight() %></td></tr>
        <tr><th>Total</th><td><b><%= bill.getTotal() %></b></td></tr>
      </table>

      <div class="actions">
        <button type="button" onclick="window.print()">Print</button>
        <a class="btn ghost" href="<%=request.getContextPath()%>/viewReservation?no=<%= bill.getReservationNo() %>">View Reservation</a>
      </div>

      <p class="muted small">Note: This bill uses (nights × rate) calculation.</p>
    </div>
    <%
      }
    %>

    <div class="footer">© Room Reservation System</div>
  </div>
</body>
</html>
