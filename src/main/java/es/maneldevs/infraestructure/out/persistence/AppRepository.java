package es.maneldevs.infraestructure.out.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.application.portout.AppPort;
import es.maneldevs.domain.model.App;

public class AppRepository implements AppPort {
    private final HikariDataSource dataSource;

    public AppRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<App> getApps() {
        String sql = "SELECT id, name FROM apps ORDER BY name ASC";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(sql);             
             var resultSet = statement.executeQuery()) {
            List<App> apps = new java.util.ArrayList<>();
            while (resultSet.next()) {
                apps.add(mapApp(resultSet));
            }
            return apps;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching apps", e);
        }
    }

    @Override
    public App getAppById(String id) {
        String sql = "SELECT id, name FROM apps WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapApp(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding app by ID", e);
        }
    }

    @Override
    public boolean existsByName(String name, String excludeId) {
        String sql = "SELECT 1 FROM apps WHERE name = ? AND (? IS NULL OR id <> ?) LIMIT 1";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, excludeId);
            statement.setString(3, excludeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking app name uniqueness", e);
        }
    }

    @Override
    public App createApp(String name) {
        String sql = "INSERT INTO apps (id, name) VALUES (?, ?) RETURNING id, name";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapApp(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating app", e);
        }
    }

    @Override
    public void updateApp(String id, String name) {
        String sql = "UPDATE apps SET name = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating app", e);
        }
    }

    private App mapApp(ResultSet resultSet) throws SQLException {
        return new App(
            resultSet.getString("id"),
            resultSet.getString("name")
        );
    }
}
