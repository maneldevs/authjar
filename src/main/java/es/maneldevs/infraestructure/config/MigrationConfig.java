package es.maneldevs.infraestructure.config;

import org.flywaydb.core.Flyway;

import com.zaxxer.hikari.HikariDataSource;

public class MigrationConfig {
    public static void init(HikariDataSource dataSource) {
        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
    }
}
