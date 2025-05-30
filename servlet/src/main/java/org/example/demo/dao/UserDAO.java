package org.example.demo.dao;

import org.example.demo.service.DBConnection;
import org.example.demo.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO user (email, password, login, status, confirmation_token) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, user.getStatus());
            stmt.setString(5, user.getConfirmToken());
            stmt.executeUpdate();
        }
    }


    public User getUserByLogin(String login) {
        return findUserByColumn("login", login);
    }

    public User getUserByEmail(String email) {
        return findUserByColumn("email", email);
    }

    public User getUserByToken(String token) throws SQLException {
        String sql = "SELECT * FROM user WHERE confirmation_token = ?";
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, token);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return parseUserFromDB(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void activateUser(int userId) throws SQLException {
        String sql = "UPDATE user SET status = 'ACTIVE', confirmation_token = NULL WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    private User parseUserFromDB(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("login"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("status"),
                rs.getString("confirmation_token")
        );
    }

    private User findUserByColumn(String columnName, String value) {
        if (!"login".equals(columnName) && !"email".equals(columnName)) {
            throw new IllegalArgumentException("Invalid column name: " + columnName);
        }

        String sql = "SELECT id, login, password, email, status, confirmation_token FROM user WHERE " + columnName + " = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, value);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return parseUserFromDB(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT id, login, password, email, status, confirmation_token FROM user WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = parseUserFromDB(rs);
                    LOGGER.info("Found user by ID: " + userId + ", login: " + user.getLogin());
                    return user;
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error finding user by ID: " + userId + ", " + e.getMessage());
            throw e;
        }
        LOGGER.warning("No user found for ID: " + userId);
        return null;
    }
    public void updateUser(User user) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE user SET login = ?, email = ?");
        boolean hasPassword = user.getPassword() != null && !user.getPassword().isEmpty();
        if (hasPassword) {
            sql.append(", password = ?");
        }
        sql.append(" WHERE id = ?");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getEmail());
            int index = 3;
            if (hasPassword) {
                // Пароль уже должен быть хешированным из EditProfileServlet
                stmt.setString(index++, user.getPassword());
            }
            stmt.setInt(index, user.getId());
            LOGGER.info("Executing update: " + sql + ", login=" + user.getLogin() + ", email=" + user.getEmail() +
                    ", hasPassword=" + hasPassword + ", id=" + user.getId());
            int rowsAffected = stmt.executeUpdate();
            LOGGER.info("Updated user ID: " + user.getId() + ", rows affected: " + rowsAffected);
            if (rowsAffected == 0) {
                throw new SQLException("No user found with ID: " + user.getId());
            }
        } catch (SQLException e) {
            LOGGER.severe("Error updating user: " + e.getMessage());
            throw e;
        }
    }
}

