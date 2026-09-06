package es.maneldevs.infraestructure.config;

import java.util.Collections;
import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.infraestructure.in.adapter.HttpController;
import gg.jte.TemplateEngine;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;

public class WebConfig {

    public static Javalin init(TemplateEngine templateEngine, HikariDataSource dataSource, SecurityFilter filter,
            List<HttpController> controllers) {
        int puerto = Integer.parseInt(Env.APP_PORT);
        Javalin app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte(templateEngine));
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "/static";
                staticFiles.hostedPath = "/static";
                staticFiles.location = Location.CLASSPATH;
            });
            config.routes.before(filter::doFilter);
            config.routes.beforeMatched(AccessManager::manageAccess);
            for (HttpController controller : controllers) {
                controller.registerRoutes(config);
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Cerrando recursos de la aplicación de forma segura...");
            if (dataSource != null) {
                dataSource.close();
            }
            app.stop();
        }));

        return app.start(puerto);
    }
}
