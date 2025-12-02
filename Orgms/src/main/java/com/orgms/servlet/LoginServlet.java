package com.orgms.servlet;


import com.orgms.dao.UserDAO;
import com.orgms.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            User u = userDAO.findByUsername(username);
            if (u != null && u.getIsActive() && BCrypt.checkpw(password, u.getPasswordHash())) {
                HttpSession s = req.getSession(true);
                s.setAttribute("userId", u.getUserId());
                s.setAttribute("username", u.getUsername());
                s.setAttribute("roleId", u.getRoleId());
                s.setAttribute("employeeId", u.getEmployeeId());
                resp.sendRedirect(req.getContextPath() + "/jsp/dashboard.jsp");
            } else {
                req.setAttribute("error", "Invalid credentials");
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
