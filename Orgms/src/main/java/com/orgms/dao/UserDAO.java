package com.orgms.dao;

import com.orgms.model.User;
import com.orgms.util.DBUtil;

import java.sql.*;
import java.util.List;

public class UserDAO {

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT user_id, username, password_hash, role_id, employee_id, is_active FROM users WHERE username=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getString("user_id"));
                    u.setUsername(rs.getString("username"));
                    u.setPasswordHash(rs.getString("password_hash"));
                    u.setRoleId(rs.getInt("role_id"));
                    u.setEmployeeId(rs.getString("employee_id"));
                    u.setIsActive(rs.getBoolean("is_active"));
                    return u;
                }
            }
        }
        return null;
    }

    public void createUser(User u) throws SQLException {
        String sql = "INSERT INTO users (user_id, username, password_hash, employee_id, role_id, is_active) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getUserId());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPasswordHash());
            ps.setString(4, u.getEmployeeId());
            ps.setInt(5, u.getRoleId());
            ps.setBoolean(6, u.getIsActive());
            ps.executeUpdate();
        }
    }

    public int countUsers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		
	}

	
}
