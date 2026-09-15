package es.maneldevs.infraestructure.out.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.application.portout.ClientPort;
import es.maneldevs.domain.model.Client;

public class ClientRepository implements ClientPort {
    private final HikariDataSource dataSource;

    public ClientRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Client> getClients() {
        String sql = "SELECT id, name, active FROM clients ORDER BY name ASC";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(sql);             
             var resultSet = statement.executeQuery()) {
            List<Client> clients = new java.util.ArrayList<>();
            while (resultSet.next()) {
                clients.add(mapClient(resultSet));
            }
            return clients;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching clients", e);
        }
    }

    @Override
    public Client getClientById(String id) {
        String sql = "SELECT id, name, active FROM clients WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapClient(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding client by ID", e);
        }
    }

    @Override
    public boolean existsByName(String name, String excludeId) {
        String sql = "SELECT 1 FROM clients WHERE name = ? AND (? IS NULL OR id <> ?) LIMIT 1";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, excludeId);
            statement.setString(3, excludeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking client name uniqueness", e);
        }
    }

    @Override
    public Client createClient(String name) {
        String sql = "INSERT INTO clients (id, name, active) VALUES (?, ?, ?) RETURNING id, name, active";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, name);
            statement.setBoolean(3, true);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapClient(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating client", e);
        }
    }

    @Override
    public void updateClient(String id, String name, boolean active) {
        String sql = "UPDATE clients SET name = ?, active = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setBoolean(2, active);
            statement.setString(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating client", e);
        }
    }

    private Client mapClient(ResultSet resultSet) throws SQLException {
        return new Client(
            resultSet.getString("id"),
            resultSet.getString("name"),
            resultSet.getBoolean("active")
        );
    }
    
}
