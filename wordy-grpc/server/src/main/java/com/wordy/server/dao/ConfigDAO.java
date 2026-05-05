package com.wordy.server.dao;

import com.wordy.server.db.DBConnection;

import java.sql.*;

/**
 * Data Access Object for game configuration.
 * Manages system-wide settings like wait_time and round_duration.
 */
public class ConfigDAO {

    /**
     * Gets a configuration value by key.
     *
     * @param key the configuration key (e.g., "wait_time", "round_duration")
     * @return the configuration value as String, or null if not found
     */
    public String getConfig(String key) {
        String sql = "SELECT value FROM config WHERE key = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("value");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets a configuration value as integer.
     *
     * @param key the configuration key
     * @param defaultValue the default value if key not found
     * @return the configuration value as int
     */
    public int getConfigAsInt(String key, int defaultValue) {
        String value = getConfig(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * Updates a configuration value.
     *
     * @param key the configuration key
     * @param value the new value
     * @return true if updated successfully
     */
    public boolean updateConfig(String key, String value) {
        // First check if key exists
        String checkSql = "SELECT COUNT(*) as count FROM config WHERE key = ?";
        String insertSql = "INSERT INTO config (key, value) VALUES (?, ?)";
        String updateSql = "UPDATE config SET value = ? WHERE key = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, key);
            ResultSet rs = checkStmt.executeQuery();

            boolean exists = false;
            if (rs.next()) {
                exists = rs.getInt("count") > 0;
            }

            // Insert or update based on existence
            PreparedStatement stmt;
            if (exists) {
                stmt = conn.prepareStatement(updateSql);
                stmt.setString(1, value);
                stmt.setString(2, key);
            } else {
                stmt = conn.prepareStatement(insertSql);
                stmt.setString(1, key);
                stmt.setString(2, value);
            }

            int rowsAffected = stmt.executeUpdate();
            stmt.close();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a configuration entry.
     *
     * @param key the configuration key
     * @return true if deleted successfully
     */
    public boolean deleteConfig(String key) {
        String sql = "DELETE FROM config WHERE key = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, key);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
