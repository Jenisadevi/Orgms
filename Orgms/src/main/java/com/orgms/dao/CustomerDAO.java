package com.orgms.dao;

import com.orgms.model.Customer;
import com.orgms.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> listAll() throws SQLException {
        List<Customer> out = new ArrayList<>();
        String sql = "SELECT customer_id, cust_name, cust_code, cust_email, cust_address, is_active FROM customers";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer cu = new Customer();
                cu.setCustomerId(rs.getString("customer_id"));
                cu.setCustName(rs.getString("cust_name"));
                cu.setCustCode(rs.getString("cust_code"));
                cu.setCustEmail(rs.getString("cust_email"));
                cu.setCustAddress(rs.getString("cust_address"));
                cu.setActive(rs.getBoolean("is_active"));
                out.add(cu);
            }
        }
        return out;
    }

    public void create(Customer cst) throws SQLException {
        String sql = "INSERT INTO customers (customer_id, cust_name, cust_code, cust_email, cust_address, is_active) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cst.getCustomerId());
            ps.setString(2, cst.getCustName());
            ps.setString(3, cst.getCustCode());
            ps.setString(4, cst.getCustEmail());
            ps.setString(5, cst.getCustAddress());
            ps.setBoolean(6, cst.isActive());
            ps.executeUpdate();
        }
    }

    public Customer findById(String id) throws SQLException {
        String sql = "SELECT customer_id, cust_name, cust_code, cust_email, cust_address, is_active FROM customers WHERE customer_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer cu = new Customer();
                    cu.setCustomerId(rs.getString("customer_id"));
                    cu.setCustName(rs.getString("cust_name"));
                    cu.setCustCode(rs.getString("cust_code"));
                    cu.setCustEmail(rs.getString("cust_email"));
                    cu.setCustAddress(rs.getString("cust_address"));
                    cu.setActive(rs.getBoolean("is_active"));
                    return cu;
                }
            }
        }
        return null;
    }
}
