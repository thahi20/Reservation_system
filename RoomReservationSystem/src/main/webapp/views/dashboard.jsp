<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="model.Reservation" %>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1"/>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
  <title>Dashboard</title>
</head>
<body>

<%
  int totalReservations = (Integer) request.getAttribute("totalReservations");
  int todayCheckins = (Integer) request.getAttribute("todayCheckins");
  long revenueEstimate = (Long) request.getAttribute("revenueEstimate");

  List<Reservation> recent =
      (List<Reservation>) request.getAttribute("recentReservations");
%>

<div class="app">

  <!-- Sidebar -->
  <aside class="sidebar">
    <div class="brand">
      <div class="logo">RR</div>
      <div>
        <div class="brandTitle">Room Reservation</div>
        <div class="brandSub muted small">Admin Panel</div>
      </div>
    </div>

    <nav class="nav">
      <a class="navItem active" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
      <a class="navItem" href="<%=request.getContextPath()%>/reservation?action=add">Add Reservation</a>
      <a class="navItem" href="<%=request.getContextPath()%>/viewReservation">View Reservation</a>
      <a class="navItem" href="<%=request.getContextPath()%>/bill">Bill</a>
      <a class="navItem" href="<%=request.getContextPath()%>/help">Help</a>
    </nav>

    <div class="sidebarBottom">
      <div class="userCard">
        <div class="avatar">
          <%= String.valueOf(session.getAttribute("user"))
                .substring(0,1).toUpperCase() %>
        </div>
        <div>
          <div class="userName">
            <%= String.valueOf(session.getAttribute("user")) %>
          </div>
          <div class="muted small">Logged in</div>
        </div>
      </div>
      <a class="btn ghost w100"
         href="<%=request.getContextPath()%>/logout">Logout</a>
    </div>
  </aside>

  <!-- Main -->
  <main class="main">

    <!-- Header + Search -->
    <div class="topbar">
      <div>
        <h1 class="pageTitle">Dashboard</h1>
        <p class="muted">Overview, stats and recent reservations</p>
      </div>

      <!-- Search box -->
      <form class="dashSearch"
            method="get"
            action="<%=request.getContextPath()%>/viewReservation">
        <input class="searchInput"
               name="no"
               placeholder="Search Reservation No (e.g., R0001)"
               required>
        <button type="submit" class="btn ghost">Search</button>
      </form>
    </div>

    <!-- Stats -->
    <section class="stats">
      <div class="statCard">
        <div class="statLabel">Total Reservations</div>
        <div class="statValue"><%= totalReservations %></div>
        <div class="muted small">All reservations in the system</div>
      </div>

      <div class="statCard">
        <div class="statLabel">Today Check-ins</div>
        <div class="statValue"><%= todayCheckins %></div>
        <div class="muted small">Based on check-in date</div>
      </div>

      <div class="statCard">
        <div class="statLabel">Revenue Estimate</div>
        <div class="statValue"><%= revenueEstimate %></div>
        <div class="muted small">Sum of (nights × room rate)</div>
      </div>
    </section>

    <!-- Recent Reservations -->
    <section class="card span2">
      <h2 class="cardTitle">Recent Reservations (Last 5)</h2>

      <table class="table">
        <thead>
          <tr>
            <th>Reservation No</th>
            <th>Guest</th>
            <th>Room</th>
            <th>Dates</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
        <%
          if (recent == null || recent.isEmpty()) {
        %>
          <tr>
            <td class="muted">—</td>
            <td class="muted">No reservations yet</td>
            <td class="muted">—</td>
            <td class="muted">—</td>
            <td></td>
          </tr>
        <%
          } else {
            for (Reservation r : recent) {
        %>
          <tr>
            <td><b><%= r.getReservationNo() %></b></td>
            <td><%= r.getGuest().getName() %></td>
            <td><span class="badge"><%= r.getRoomType() %></span></td>
            <td><%= r.getCheckIn() %> → <%= r.getCheckOut() %></td>
            <td>
              <a class="btn ghost"
                 href="<%=request.getContextPath()%>/viewReservation?no=<%= r.getReservationNo() %>">View</a>
              <a class="btn ghost"
                 href="<%=request.getContextPath()%>/bill?no=<%= r.getReservationNo() %>">Bill</a>
            </td>
          </tr>
        <%
            }
          }
        %>
        </tbody>
      </table>
    </section>

    <div class="footer">© Room Reservation System</div>
  </main>
</div>

</body>
</html>

