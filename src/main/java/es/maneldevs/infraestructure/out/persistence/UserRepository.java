package es.maneldevs.infraestructure.out.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

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

    @Override
    public User createUser(String email, String passwordHash, String role) {
        String sql = "INSERT INTO users (id, email, password_hash, user_role, active) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, email);
            statement.setString(3, passwordHash);
            statement.setString(4, role);
            statement.setBoolean(5, true);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String userId = resultSet.getString("id");
                    return new User(userId, email, passwordHash, role, true);
                } else {
                    throw new RuntimeException("Error creating user: no ID returned");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
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
