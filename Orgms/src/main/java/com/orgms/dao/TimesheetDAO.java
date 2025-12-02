package com.orgms.dao;

import com.orgms.model.Timesheet;
import com.orgms.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimesheetDAO {

    public void create(Timesheet t) throws SQLException {
        String sql = "INSERT INTO timesheets (timesheet_id, user_id, employee_id, project_id, task_id, week_start_date, week_end_date, day_hours, weekly_total, status, created_by) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getTimesheetId());
            ps.setString(2, t.getUserId());
            ps.setString(3, t.getEmployeeId());
            ps.setString(4, t.getProjectId());
            ps.setString(5, t.getTaskId());
            if (t.getWeekStart()!=null) ps.setDate(6, new java.sql.Date(t.getWeekStart().getTime())); else ps.setNull(6, Types.DATE);
            if (t.getWeekEnd()!=null) ps.setDate(7, new java.sql.Date(t.getWeekEnd().getTime())); else ps.setNull(7, Types.DATE);
            ps.setString(8, t.getDayHoursJson());
            ps.setDouble(9, t.getWeeklyTotal());
            ps.setString(10, t.getStatus());
            ps.setString(11, t.getUserId());
            ps.executeUpdate();
        }
    }

    public List<Timesheet> listByEmployee(String empId) throws SQLException {
        List<Timesheet> out = new ArrayList<>();
        String sql = "SELECT timesheet_id, user_id, employee_id, project_id, day_hours, weekly_total, status, created_at FROM timesheets WHERE employee_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timesheet t = new Timesheet();
                    t.setTimesheetId(rs.getString("timesheet_id"));
                    t.setUserId(rs.getString("user_id"));
                    t.setEmployeeId(rs.getString("employee_id"));
                    t.setProjectId(rs.getString("project_id"));
                    t.setDayHoursJson(rs.getString("day_hours"));
                    t.setWeeklyTotal(rs.getDouble("weekly_total"));
                    t.setStatus(rs.getString("status"));
                    out.add(t);
                }
            }
        }
        return out;
    }

    public void updateStatus(String timesheetId, String status) throws SQLException {
        String sql = "UPDATE timesheets SET status=? WHERE timesheet_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, timesheetId);
            ps.executeUpdate();
        }
    }
}
