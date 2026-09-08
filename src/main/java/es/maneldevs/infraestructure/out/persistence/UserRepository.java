package es.maneldevs.infraestructure.out.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.application.portout.UserPort;
import es.maneldevs.domain.model.User;

public class UserRepository implements UserPort {
    private final HikariDataSource dataSource;

    public UserRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getUserLoggedFromSessionNotExpired(String sessionId) {
        String sql = """
                SELECT u.id, u.email, u.password_hash, u.user_role, u.active
                FROM sessions s
                JOIN users u ON u.id = s.user_id
                WHERE s.id = ? AND s.expires_at > NOW() AND u.active = TRUE
                """;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sessionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapUser(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by session", e);
        }
    }

    @Override
    public User getActiveUserById(String userId) {
        String sql = "SELECT id, email, password_hash, user_role, active FROM users WHERE id = ? AND active = TRUE";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapUser(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active user by ID", e);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT id, email, password_hash, user_role, active FROM users WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapUser(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email", e);
        }
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString("id"),
                resultSet.getString("email"),
                resultSet.getString("password_hash"),
                resultSet.getString("user_role"),
                resultSet.getBoolean("active"));
    }

}
