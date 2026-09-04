package es.maneldevs;

import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.infraestructure.config.Container;
import es.maneldevs.infraestructure.config.DatabaseConfig;
import es.maneldevs.infraestructure.config.EnvLoaderConfig;
import es.maneldevs.infraestructure.config.MigrationConfig;
import es.maneldevs.infraestructure.config.TemplateEngineConfig;
import es.maneldevs.infraestructure.config.WebConfig;
import es.maneldevs.infraestructure.in.HttpController;
import gg.jte.TemplateEngine;

public class Main {

    public static void main(String[] args) {
        EnvLoaderConfig.init();
        HikariDataSource dataSource = DatabaseConfig.init();
        MigrationConfig.init(dataSource);
        TemplateEngine templateEngine = TemplateEngineConfig.init();
        List<HttpController> controllers = Container.buildControllers(dataSource);
        WebConfig.init(templateEngine, dataSource, controllers);
    }
}
