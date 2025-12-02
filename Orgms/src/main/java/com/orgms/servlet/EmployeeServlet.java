package com.orgms.servlet;

import com.orgms.dao.EmployeeDAO;
import com.orgms.model.Employee;
import com.orgms.dao.RecordTrackingDAO;
import com.orgms.util.JsonUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final RecordTrackingDAO trackingDAO = new RecordTrackingDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";
        try {
            switch (action) {
                case "create":
                    req.getRequestDispatcher("/jsp/employee/form.jsp").forward(req, resp);
                    break;
                case "edit":
                    String id = req.getParameter("id");
                    req.setAttribute("employee", employeeDAO.findById(id));
                    req.getRequestDispatcher("/jsp/employee/form.jsp").forward(req, resp);
                    break;
                default:
                    List<Employee> list = employeeDAO.listAll();
                    req.setAttribute("employees", list);
                    req.getRequestDispatcher("/jsp/employee/list.jsp").forward(req, resp);
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
                String id = req.getParameter("employeeId");
                Employee e = readEmployeeFromRequest(req);
                if (id == null || id.isEmpty()) {
                    // create
                    e.setEmployeeId(UUID.randomUUID().toString());
                    e.setEmployeeCode("E" + System.currentTimeMillis());
                    employeeDAO.create(e);
                    trackingDAO.log("Employee", "CREATE", e.getEmployeeId(), (String) req.getSession().getAttribute("userId"), (String) req.getSession().getAttribute("username"), JsonUtil.toJson(e));
                } else {
                    e.setEmployeeId(id);
                    employeeDAO.update(e);
                    trackingDAO.log("Employee", "UPDATE", e.getEmployeeId(), (String) req.getSession().getAttribute("userId"), (String) req.getSession().getAttribute("username"), JsonUtil.toJson(e));
                }
                resp.sendRedirect(req.getContextPath() + "/employees");
            } else {
                resp.sendRedirect(req.getContextPath() + "/employees");
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private Employee readEmployeeFromRequest(HttpServletRequest req) {
        Employee e = new Employee();
        e.setEmpName(req.getParameter("name"));
        e.setEmpCemail(req.getParameter("email"));
        e.setEmpAddress(req.getParameter("address"));
        e.setEmpDesignation(req.getParameter("designation"));
        e.setEmpBgp(req.getParameter("bloodGroup"));
        e.setEmpCmob(req.getParameter("mobile"));
        e.setActive(true);
        String dob = req.getParameter("dob");
        try {
            if (dob != null && !dob.isEmpty()) e.setEmpDob(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
        } catch (Exception ignored) {}
        return e;
    }
}
