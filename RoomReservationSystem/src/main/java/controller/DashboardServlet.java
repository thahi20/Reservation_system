package controller;

import dao.RateStore;
import dao.ReservationStore;
import model.Reservation;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.requireLogin(req, resp)) return;

        ReservationStore store = ReservationStore.getInstance(getServletContext());
        List<Reservation> all = store.findAll();

        int totalReservations = all.size();

        LocalDate today = LocalDate.now();
        int todayCheckins = 0;

        // Revenue estimate: sum(nights * rate)
        long revenueEstimate = 0;
        Map<String, Integer> rates = RateStore.getInstance(getServletContext()).getRates();

        for (Reservation r : all) {
            if (r.getCheckIn() != null && r.getCheckIn().equals(today)) {
                todayCheckins++;
            }

            if (r.getCheckIn() != null && r.getCheckOut() != null) {
                long nights = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
                int rate = rates.getOrDefault(r.getRoomType().toUpperCase(Locale.ROOT), 0);
                if (nights > 0) revenueEstimate += nights * (long) rate;
            }
        }

        // Last 5 reservations (latest by file order)
        List<Reservation> recent = new ArrayList<>(all);
        Collections.reverse(recent);
        if (recent.size() > 5) recent = recent.subList(0, 5);

        req.setAttribute("totalReservations", totalReservations);
        req.setAttribute("todayCheckins", todayCheckins);
        req.setAttribute("revenueEstimate", revenueEstimate);
        req.setAttribute("recentReservations", recent);

        req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
    }
}
