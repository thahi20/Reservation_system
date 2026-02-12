package controller;

import dao.UserStore;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Retrieve username and password
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserStore users = UserStore.getInstance(getServletContext());

        //validate the username and password
        if (users.validate(username, password)) {
            //if valid create a session and store the username
            req.getSession(true).setAttribute("user", username);
            //redirect user to dashboard after successful login
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            //If invalid, set an error message and forward back to login page
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
