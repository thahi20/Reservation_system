package controller;

import dao.RateStore;
import dao.ReservationStore;
import model.Guest;
import model.Reservation;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.Map;

public class ReservationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.requireLogin(req, resp)) {
            return;
        }

        String action = req.getParameter("action");
        if (action == null) {
            action = "add";
        }

        if ("view".equalsIgnoreCase(action)) {
            req.getRequestDispatcher("/views/view-reservation.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/views/add-reservation.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.requireLogin(req, resp)) {
            return;
        }

        String reservationNo = trim(req.getParameter("reservationNo"));
        String guestName = trim(req.getParameter("guestName"));
        String address = trim(req.getParameter("address"));
        String contact = trim(req.getParameter("contact"));
        String roomType = trim(req.getParameter("roomType")).toUpperCase(Locale.ROOT);
        String checkInStr = trim(req.getParameter("checkIn"));
        String checkOutStr = trim(req.getParameter("checkOut"));

        // Validation
        if (!reservationNo.matches("R\\d{4}")) {
            req.setAttribute("error", "Reservation No must be like R0001");
            req.getRequestDispatcher("/views/add-reservation.jsp").forward(req, resp);
            return;
        }
        if (guestName.isEmpty() || address.isEmpty()) {
            req.setAttribute("error", "Guest name and address are required");
            req.getRequestDispatcher("/views/add-reservation.jsp").forward(req, resp);
            return;
        }
        if (!contact.matches("(\\+94\\d{9}|0\\d{9}|\\d{10})")) {
            req.setAttribute("error", "Contact must be 10 digits (or +94xxxxxxxxx)");
            req.getRequestDispatcher("/views/add-reservation.jsp").forward(req, resp);
            return;
        }

        LocalDate checkIn, checkOut;
        try {
            checkIn = LocalDate.parse(checkInStr);
            checkOut = LocalDate.parse(checkOutStr);
        } catch (Exception e) {
            req.setAttribute("error", "Dates must be in YYYY-MM-DD format");
            req.getRequestDispatcher("/views/add-reservation.jsp").forward(req, resp);
            return;
        }
        if (!checkOut.isAfter(checkIn)) {
            req.setAttribute("error", "Check-out must be after check-in");
            req.getRequestDispatcher("/views/add-reservation.jsp").forward(req, resp);
            return;
        }

        // Validate room type exists
        Map<String, Integer> rates = RateStore.getInstance(getServletContext()).getRates();
        if (!rates.containsKey(roomType)) {
            req.setAttribute("error", "Invalid room type");
            req.getRequestDispatcher("/views/add-reservation.jsp").forward(req, resp);
            return;
        }

        ReservationStore store = ReservationStore.getInstance(getServletContext());
        if (store.exists(reservationNo)) {
            req.setAttribute("error", "Reservation number already exists");
            req.getRequestDispatcher("/views/add-reservation.jsp").forward(req, resp);
            return;
        }

        Guest guest = new Guest(guestName, address, contact);
        Reservation r = new Reservation(reservationNo, guest, roomType, checkIn, checkOut, OffsetDateTime.now().toString());

        store.add(r);

        // PRG pattern: redirect after POST
        resp.sendRedirect(req.getContextPath() + "/reservation?action=view&no=" + reservationNo);
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
