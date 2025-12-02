package com.orgms.dao;

import com.orgms.util.DBUtil;

import java.sql.*;

public class RecordTrackingDAO {

    public void log(String module, String method, String targetId, String userId, String userName, String changedFieldsJson) throws SQLException {
        String sql = "INSERT INTO record_tracking (module_name, method, target_id, user_id, user_name, changed_fields) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, module);
            ps.setString(2, method);
            ps.setString(3, targetId);
            ps.setString(4, userId);
            ps.setString(5, userName);
            ps.setString(6, changedFieldsJson);
            ps.executeUpdate();
        }
    }
}

