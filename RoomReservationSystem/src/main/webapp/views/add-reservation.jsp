<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1"/>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
  <title>Add Reservation</title>
</head>
<body>
  
  <div class="container">
    <div class="header">
      <div>
        <h1>Add Reservation</h1>
        <p class="muted">Enter reservation details</p>
      </div>
      <div class="actions">
        <a class="btn ghost" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
        <a class="btn ghost" href="<%=request.getContextPath()%>/logout">Logout</a>
      </div>
    </div>

    <div class="card">
      <h2 class="cardTitle">Reservation Form</h2>

      <form method="post" action="<%=request.getContextPath()%>/reservation">
        <div class="grid2">
          <label>Reservation No (R0001)
            <input name="reservationNo" placeholder="R0001" required>
          </label>

          <label>Room Type
            <select name="roomType" required>
              <option value="STANDARD">STANDARD</option>
              <option value="DELUXE">DELUXE</option>
              <option value="SUITE">SUITE</option>
            </select>
          </label>

          <label>Guest Name
            <input name="guestName" placeholder="Kasun Perera" required>
          </label>

          <label>Contact Number
            <input name="contact" placeholder="0771234567" required>
          </label>

          <label>Address
            <input name="address" placeholder="Galle" required>
          </label>

          <div></div>

          <label>Check-In
          <%
  String today = java.time.LocalDate.now().toString();
%>
          
  <input type="date" name="checkIn" min="<%= today %>" required>
</label>

<label>Check-Out
  <input type="date" name="checkOut" min="<%= today %>" required>
</label>

        <div class="actions">
          <button type="submit">Save Reservation</button>
          <a class="btn ghost" href="<%=request.getContextPath()%>/viewReservation">View Reservation</a>
        </div>

        <div class="notice error">
          <%
            Object err = request.getAttribute("error");
            if (err != null) out.print(err.toString());
          %>
        </div>
      </form>
    </div>

    <div class="footer">© Room Reservation System</div>
  </div>
</body>
</html>
