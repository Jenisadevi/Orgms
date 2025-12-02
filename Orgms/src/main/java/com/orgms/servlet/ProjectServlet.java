package com.orgms.servlet;


import com.orgms.dao.ProjectDAO;
import com.orgms.dao.CustomerDAO;
import com.orgms.dao.RecordTrackingDAO;
import com.orgms.model.Project;
import com.orgms.model.Customer;
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

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final RecordTrackingDAO trackingDAO = new RecordTrackingDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";
        try {
            switch (action) {
                case "create":
                    req.setAttribute("customers", customerDAO.listAll());
                    req.getRequestDispatcher("/jsp/project/form.jsp").forward(req, resp);
                    break;
                case "edit":
                    String id = req.getParameter("id");
                    req.setAttribute("project", projectDAO.findById(id));
                    req.setAttribute("customers", customerDAO.listAll());
                    req.getRequestDispatcher("/jsp/project/form.jsp").forward(req, resp);
                    break;
                default:
                    List<Project> list = projectDAO.listAll();
                    req.setAttribute("projects", list);
                    req.getRequestDispatcher("/jsp/project/list.jsp").forward(req, resp);
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
                String id = req.getParameter("projectId");
                Project p = new Project();
                p.setJobName(req.getParameter("jobName"));
                p.setCustomerId(req.getParameter("customerId"));
                p.setTotalCost(Double.parseDouble(req.getParameter("totalCost")==null?"0":req.getParameter("totalCost")));
                p.setTotalHours(Double.parseDouble(req.getParameter("totalHours")==null?"0":req.getParameter("totalHours")));
                p.setStatus(req.getParameter("status"));
                p.setCreatedBy((String) req.getSession().getAttribute("userId"));
                if (id == null || id.isEmpty()) {
                    p.setProjectId(UUID.randomUUID().toString());
                    // get custCode
                    Customer cu = customerDAO.findById(p.getCustomerId());
                    String custCode = (cu!=null?cu.getCustCode():"C000");
                    projectDAO.create(p, custCode);
                    trackingDAO.log("Project","CREATE", p.getProjectId(), (String)req.getSession().getAttribute("userId"), (String)req.getSession().getAttribute("username"), JsonUtil.toJson(p));
                } else {
                    p.setProjectId(id);
                    // for update read existing job_code and append version - for brevity we store as-is
                    projectDAO.update(p);
                    trackingDAO.log("Project","UPDATE", p.getProjectId(), (String)req.getSession().getAttribute("userId"), (String)req.getSession().getAttribute("username"), JsonUtil.toJson(p));
                }
                resp.sendRedirect(req.getContextPath() + "/projects");
            } else {
                resp.sendRedirect(req.getContextPath() + "/projects");
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
