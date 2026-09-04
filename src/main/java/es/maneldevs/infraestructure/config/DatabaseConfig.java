package es.maneldevs.infraestructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConfig {

    public static HikariDataSource init() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(Env.DB_URL);
        config.setUsername(Env.DB_USER);
        config.setPassword(Env.DB_PASSWORD);
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        return new HikariDataSource(config);
    }
}
