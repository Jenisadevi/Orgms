package com.orgms.servlet;

import com.orgms.dao.CustomerDAO;
import com.orgms.model.Customer;
import com.orgms.dao.RecordTrackingDAO;
import com.orgms.util.JsonUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final RecordTrackingDAO trackingDAO = new RecordTrackingDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";
        try {
            switch (action) {
                case "create":
                    req.getRequestDispatcher("/jsp/customer/form.jsp").forward(req, resp);
                    break;
                case "edit":
                    String id = req.getParameter("id");
                    req.setAttribute("customer", customerDAO.findById(id));
                    req.getRequestDispatcher("/jsp/customer/form.jsp").forward(req, resp);
                    break;
                default:
                    List<Customer> list = customerDAO.listAll();
                    req.setAttribute("customers", list);
                    req.getRequestDispatcher("/jsp/customer/list.jsp").forward(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("save".equals(action)) {
                String id = req.getParameter("customerId");
                Customer c = new Customer();
                c.setCustName(req.getParameter("name"));
                c.setCustEmail(req.getParameter("email"));
                c.setCustAddress(req.getParameter("address"));
                c.setActive(true);
                if (id == null || id.isEmpty()) {
                    c.setCustomerId(UUID.randomUUID().toString());
                    c.setCustCode("C" + System.currentTimeMillis());
                    customerDAO.create(c);
                    trackingDAO.log("Customer", "CREATE", c.getCustomerId(), (String) req.getSession().getAttribute("userId"), (String) req.getSession().getAttribute("username"), JsonUtil.toJson(c));
                } else {
                    // update not implemented fully for brevity
                }
                resp.sendRedirect(req.getContextPath() + "/customers");
            } else {
                resp.sendRedirect(req.getContextPath() + "/customers");
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
