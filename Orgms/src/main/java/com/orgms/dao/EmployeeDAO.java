package com.orgms.dao;

import com.orgms.model.Employee;
import com.orgms.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public List<Employee> listAll() throws SQLException {
        List<Employee> out = new ArrayList<>();
        String sql = "SELECT employee_id, employee_code, emp_name, emp_cemail, emp_designation, emp_bgp, emp_cmob, is_active FROM employees";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Employee e = new Employee();
                e.setEmployeeId(rs.getString("employee_id"));
                e.setEmployeeCode(rs.getString("employee_code"));
                e.setEmpName(rs.getString("emp_name"));
                e.setEmpCemail(rs.getString("emp_cemail"));
                e.setEmpDesignation(rs.getString("emp_designation"));
                e.setEmpBgp(rs.getString("emp_bgp"));
                e.setEmpCmob(rs.getString("emp_cmob"));
                e.setActive(rs.getBoolean("is_active"));
                out.add(e);
            }
        }
        return out;
    }

    public void create(Employee e) throws SQLException {
        String sql = "INSERT INTO employees (employee_id, employee_code, emp_name, emp_cemail, emp_dob, emp_address, emp_designation, emp_bgp, emp_cmob, is_active) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getEmployeeId());
            ps.setString(2, e.getEmployeeCode());
            ps.setString(3, e.getEmpName());
            ps.setString(4, e.getEmpCemail());
            if (e.getEmpDob() != null) ps.setDate(5, new java.sql.Date(e.getEmpDob().getTime())); else ps.setNull(5, Types.DATE);
            ps.setString(6, e.getEmpAddress());
            ps.setString(7, e.getEmpDesignation());
            ps.setString(8, e.getEmpBgp());
            ps.setString(9, e.getEmpCmob());
            ps.setBoolean(10, e.isActive());
            ps.executeUpdate();
        }
    }

    public Employee findById(String id) throws SQLException {
        String sql = "SELECT employee_id, employee_code, emp_name, emp_cemail, emp_designation, emp_bgp, emp_cmob, is_active FROM employees WHERE employee_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee e = new Employee();
                    e.setEmployeeId(rs.getString("employee_id"));
                    e.setEmployeeCode(rs.getString("employee_code"));
                    e.setEmpName(rs.getString("emp_name"));
                    e.setEmpCemail(rs.getString("emp_cemail"));
                    e.setEmpDesignation(rs.getString("emp_designation"));
                    e.setEmpBgp(rs.getString("emp_bgp"));
                    e.setEmpCmob(rs.getString("emp_cmob"));
                    e.setActive(rs.getBoolean("is_active"));
                    return e;
                }
            }
        }
        return null;
    }

    public void update(Employee e) throws SQLException {
        String sql = "UPDATE employees SET emp_name=?, emp_cemail=?, emp_dob=?, emp_address=?, emp_designation=?, emp_bgp=?, emp_cmob=?, is_active=? WHERE employee_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getEmpName());
            ps.setString(2, e.getEmpCemail());
            if (e.getEmpDob() != null) ps.setDate(3, new java.sql.Date(e.getEmpDob().getTime())); else ps.setNull(3, Types.DATE);
            ps.setString(4, e.getEmpAddress());
            ps.setString(5, e.getEmpDesignation());
            ps.setString(6, e.getEmpBgp());
            ps.setString(7, e.getEmpCmob());
            ps.setBoolean(8, e.isActive());
            ps.setString(9, e.getEmployeeId());
            ps.executeUpdate();
        }
    }
}

