package es.maneldevs.infraestructure.out.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.application.portout.SessionPort;
import es.maneldevs.domain.model.Session;

public class SessionRepository implements SessionPort {
    private final HikariDataSource dataSource;

    public SessionRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveSession(Session session) {
        String sql = "INSERT INTO sessions (id, expires_at, user_id) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, session.id());
            statement.setTimestamp(2, Timestamp.from(session.expiresAt()));
            statement.setString(3, session.user().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving session", e);
        }
    }

}
