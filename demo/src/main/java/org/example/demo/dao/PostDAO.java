package org.example.demo.dao;

import org.example.demo.model.Post;
import org.example.demo.model.User;
import org.example.demo.service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostDAO {
    public void createPost(Post post) throws SQLException {
        String sql = "INSERT INTO post (user_id, title, description) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, post.getUserId());
            stmt.setString(2, post.getTitle());
            stmt.setString(3, post.getDescription());
            stmt.executeUpdate();
        }
    }
    private Post findPostByUserId(int userId) throws SQLException {
        String sql = "SELECT user_id, title, description FROM post WHERE = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return parsePostFromDB(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Post parsePostFromDB(ResultSet rs) throws SQLException {
        return new Post(
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getString("description")
        );
    }
}
