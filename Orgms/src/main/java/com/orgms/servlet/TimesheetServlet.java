package com.orgms.servlet;

import com.orgms.dao.TimesheetDAO;

import com.orgms.dao.ProjectDAO;
import com.orgms.dao.RecordTrackingDAO;
import com.orgms.model.Timesheet;
import com.orgms.model.Project;
import com.orgms.util.JsonUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/timesheets")
public class TimesheetServlet extends HttpServlet {
    private final TimesheetDAO tsDAO = new TimesheetDAO();
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final RecordTrackingDAO trackingDAO = new RecordTrackingDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";
        String empId = (String) req.getSession().getAttribute("employeeId");
        try {
            switch (action) {
                case "create":
                    // show form with project list excluding Completed
                    List<Project> plist = projectDAO.listAll();
                    List<Project> active = new ArrayList<>();
                    for (Project p: plist) if (!"Completed".equalsIgnoreCase(p.getStatus())) active.add(p);
                    req.setAttribute("projects", active);
                    req.getRequestDispatcher("/jsp/timesheet/form.jsp").forward(req, resp);
                    break;
                default:
                    // list timesheets for this employee
                    List<com.orgms.model.Timesheet> list = tsDAO.listByEmployee(empId);
                    req.setAttribute("timesheets", list);
                    req.getRequestDispatcher("/jsp/timesheet/list.jsp").forward(req, resp);
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
                Timesheet t = new Timesheet();
                t.setTimesheetId(UUID.randomUUID().toString());
                t.setUserId((String)req.getSession().getAttribute("userId"));
                t.setEmployeeId((String)req.getSession().getAttribute("employeeId"));
                t.setProjectId(req.getParameter("projectId"));
                // collect up to 16 day inputs named day1..day16
                List<Double> days = new ArrayList<>();
                double total = 0d;
                for (int i=1;i<=16;i++) {
                    String v = req.getParameter("day"+i);
                    double dv = 0;
                    if (v!=null && !v.isEmpty()) {
                        try { dv = Double.parseDouble(v); } catch(Exception ignored){}
                    }
                    days.add(dv);
                    total += dv;
                }
                t.setDayHoursJson(JsonUtil.toJson(days));
                t.setWeeklyTotal(total);
                t.setStatus("Draft");
                tsDAO.create(t);
                trackingDAO.log("Timesheet","CREATE", t.getTimesheetId(), (String)req.getSession().getAttribute("userId"), (String)req.getSession().getAttribute("username"), JsonUtil.toJson(t));
                resp.sendRedirect(req.getContextPath() + "/timesheets");
            } else if ("submit".equals(action)) {
                String id = req.getParameter("timesheetId");
                tsDAO.updateStatus(id, "Submitted");
                trackingDAO.log("Timesheet","SUBMIT", id, (String)req.getSession().getAttribute("userId"), (String)req.getSession().getAttribute("username"), "{}");
                // notify principal - simplified: send to fixed email
                try { com.orgms.util.EmailUtil.send("principal@example.com","Timesheet submitted","Timesheet "+id+" submitted."); } catch(Exception e){ e.printStackTrace(); }
                resp.sendRedirect(req.getContextPath() + "/timesheets");
            } else {
                resp.sendRedirect(req.getContextPath() + "/timesheets");
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
