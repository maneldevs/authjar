package es.maneldevs;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.infraestructure.config.Container;
import es.maneldevs.infraestructure.config.DatabaseConfig;
import es.maneldevs.infraestructure.config.EnvLoaderConfig;
import es.maneldevs.infraestructure.config.MigrationConfig;
import es.maneldevs.infraestructure.config.TemplateEngineConfig;
import es.maneldevs.infraestructure.config.WebConfig;
import gg.jte.TemplateEngine;

public class Main {

    public static void main(String[] args) {
        EnvLoaderConfig.init();
        HikariDataSource dataSource = DatabaseConfig.init();
        MigrationConfig.init(dataSource);
        TemplateEngine templateEngine = TemplateEngineConfig.init();
        Container container = new Container(dataSource);
        WebConfig.init(templateEngine, dataSource, container.securityFilter, container.controllers);
    }
}
