package com.orgms.dao;

import com.orgms.model.Project;
import com.orgms.util.DBUtil;
import com.google.gson.*;
import java.sql.*;
import java.util.*;

public class ProjectDAO {

    public List<Project> listAll() throws SQLException {
        List<Project> out = new ArrayList<>();
        String sql = "SELECT project_id, job_name, customer_id, job_code, totalCost, totalHours, status FROM projects WHERE is_archived=0";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Project p = new Project();
                p.setProjectId(rs.getString("project_id"));
                p.setJobName(rs.getString("job_name"));
                p.setCustomerId(rs.getString("customer_id"));
                p.setJobCodeJson(rs.getString("job_code"));
                p.setTotalCost(rs.getDouble("totalCost"));
                p.setTotalHours(rs.getDouble("totalHours"));
                p.setStatus(rs.getString("status"));
                out.add(p);
            }
        }
        return out;
    }

    public void create(Project p, String custCode) throws SQLException {
        // Create initial job_code JSON with version
        JsonObject root = new JsonObject();
        JsonArray versions = new JsonArray();
        // generate serial: for demo use timestamp-based serial
        String serial = String.format("%04d", new Random().nextInt(9999));
        String jobCodeStr = custCode + "." + serial;
        JsonObject v = new JsonObject();
        v.addProperty("code", jobCodeStr);
        v.addProperty("version", "1");
        v.addProperty("createdAt", new java.util.Date().toString());
        v.addProperty("note", "initial");
        versions.add(v);
        root.add("versions", versions);
        String jobCodeJson = root.toString();

        String sql = "INSERT INTO projects (project_id, job_name, customer_id, job_code, totalCost, totalHours, status, created_by) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getProjectId());
            ps.setString(2, p.getJobName());
            ps.setString(3, p.getCustomerId());
            ps.setString(4, jobCodeJson);
            ps.setDouble(5, p.getTotalCost());
            ps.setDouble(6, p.getTotalHours());
            ps.setString(7, p.getStatus()==null?"Open":p.getStatus());
            ps.setString(8, p.getCreatedBy());
            ps.executeUpdate();
        }
    }

    public Project findById(String id) throws SQLException {
        String sql = "SELECT project_id, job_name, customer_id, job_code, totalCost, totalHours, status FROM projects WHERE project_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Project p = new Project();
                    p.setProjectId(rs.getString("project_id"));
                    p.setJobName(rs.getString("job_name"));
                    p.setCustomerId(rs.getString("customer_id"));
                    p.setJobCodeJson(rs.getString("job_code"));
                    p.setTotalCost(rs.getDouble("totalCost"));
                    p.setTotalHours(rs.getDouble("totalHours"));
                    p.setStatus(rs.getString("status"));
                    return p;
                }
            }
        }
        return null;
    }

    public void update(Project p) throws SQLException {
        String sql = "UPDATE projects SET job_name=?, job_code=?, totalCost=?, totalHours=?, status=?, updated_by=? WHERE project_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getJobName());
            ps.setString(2, p.getJobCodeJson());
            ps.setDouble(3, p.getTotalCost());
            ps.setDouble(4, p.getTotalHours());
            ps.setString(5, p.getStatus());
            ps.setString(6, p.getCreatedBy());
            ps.setString(7, p.getProjectId());
            ps.executeUpdate();
        }
    }
}
