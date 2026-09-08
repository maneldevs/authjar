package es.maneldevs.infraestructure.out.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.application.portout.ApiKeyPort;

public class ApiKeyRepository implements ApiKeyPort {
    private final HikariDataSource dataSource;

    public ApiKeyRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean existsActiveApiKey(String hashedApiKey) {
        String sql = "SELECT 1 FROM api_keys WHERE api_key_hash = ? AND active = TRUE";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, hashedApiKey);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking active API key", e);
        }
    }

}
