package controller;

import dao.ReservationStore;
import model.Reservation;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ViewReservationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.requireLogin(req, resp)) return;

        String no = req.getParameter("no");
        if (no != null && !no.trim().isEmpty()) {
            ReservationStore store = ReservationStore.getInstance(getServletContext());
            Reservation r = store.findByNo(no.trim());
            if (r == null) {
                req.setAttribute("error", "Reservation not found");
            } else {
                req.setAttribute("reservation", r);
            }
        }
        req.getRequestDispatcher("/views/view-reservation.jsp").forward(req, resp);
    }
}
