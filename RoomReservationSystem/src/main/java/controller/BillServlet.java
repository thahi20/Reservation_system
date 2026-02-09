package controller;

import dao.RateStore;
import dao.ReservationStore;
import model.Bill;
import model.Reservation;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.temporal.ChronoUnit;

public class BillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.requireLogin(req, resp)) return;

        String no = req.getParameter("no");
        if (no != null && !no.trim().isEmpty()) {
            Reservation r = ReservationStore.getInstance(getServletContext()).findByNo(no.trim());
            if (r == null) {
                req.setAttribute("error", "Reservation not found");
            } else {
                long nights = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
                int rate = RateStore.getInstance(getServletContext()).getRate(r.getRoomType());
                long total = nights * (long) rate;

                Bill bill = new Bill(r.getReservationNo(), r.getGuest().getName(), r.getRoomType(),
                        nights, rate, total);

                req.setAttribute("bill", bill);
                req.setAttribute("reservation", r);
            }
        }
        req.getRequestDispatcher("/views/bill.jsp").forward(req, resp);
    }
}
