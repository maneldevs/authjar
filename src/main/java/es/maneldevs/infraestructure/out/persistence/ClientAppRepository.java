package es.maneldevs.infraestructure.out.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.application.portout.ClientAppPort;
import es.maneldevs.domain.model.App;
import es.maneldevs.domain.model.Client;
import es.maneldevs.domain.model.ClientApp;

public class ClientAppRepository implements ClientAppPort {
    private static final String SELECT_BASE =
            "SELECT ca.id, ca.name, ca.active, ca.api_key_hash, c.id AS client_id, c.name AS client_name, " +
            "a.id AS app_id, a.name AS app_name " +
            "FROM client_apps ca " +
            "JOIN clients c ON c.id = ca.client_id " +
            "JOIN apps a ON a.id = ca.app_id ";

    private final HikariDataSource dataSource;

    public ClientAppRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<ClientApp> getClientApps(String clientId) {
        String sql = SELECT_BASE + "WHERE ca.client_id = ? ORDER BY ca.name ASC";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<ClientApp> clientApps = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    clientApps.add(mapClientApp(resultSet));
                }
                return clientApps;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching client apps", e);
        }
    }

    @Override
    public ClientApp getClientApp(String clientId, String appId) {
        String sql = SELECT_BASE + "WHERE ca.client_id = ? AND ca.app_id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, clientId);
            statement.setString(2, appId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapClientApp(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding client app", e);
        }
    }

    @Override
    public ClientApp getActiveClientAppByApiKeyHash(String apiKeyHash) {
        String sql = SELECT_BASE + "WHERE ca.api_key_hash = ? AND ca.active = TRUE";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, apiKeyHash);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapClientApp(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding client app by API key hash", e);
        }
    }

    @Override
    public boolean existsByName(String name, String excludeId) {
        String sql = "SELECT 1 FROM client_apps WHERE name = ? AND (? IS NULL OR id <> ?) LIMIT 1";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, excludeId);
            statement.setString(3, excludeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking client app name uniqueness", e);
        }
    }

    @Override
    public ClientApp createClientApp(String clientId, String appId, String name, String apiKeyHash) {
        String id = UUID.randomUUID().toString();
        String sql = "INSERT INTO client_apps (id, name, active, client_id, app_id, api_key_hash) VALUES (?, ?, TRUE, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, clientId);
            statement.setString(4, appId);
            statement.setString(5, apiKeyHash);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating client app", e);
        }
        return findById(id);
    }

    @Override
    public void updateClientApp(String id, String name, Boolean active, String apiKeyHash) {
        String sql = "UPDATE client_apps SET name = ?, active = ?, api_key_hash = COALESCE(?, api_key_hash) WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setBoolean(2, active);
            statement.setString(3, apiKeyHash);
            statement.setString(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating client app", e);
        }
    }

    private ClientApp findById(String id) {
        String sql = SELECT_BASE + "WHERE ca.id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapClientApp(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding client app by ID", e);
        }
    }

    private ClientApp mapClientApp(ResultSet resultSet) throws SQLException {
        Client client = new Client(
                resultSet.getString("client_id"),
                resultSet.getString("client_name"));
        App app = new App(
                resultSet.getString("app_id"),
                resultSet.getString("app_name"));
        return new ClientApp(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getBoolean("active"),
                client,
                app,
                resultSet.getString("api_key_hash"));
    }

}
