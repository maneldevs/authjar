package es.maneldevs.infraestructure.config;

import java.util.Collections;
import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.infraestructure.in.HttpController;
import gg.jte.TemplateEngine;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

public class WebConfig {

    public static Javalin init(TemplateEngine templateEngine, HikariDataSource dataSource,
            List<HttpController> controllers) {
        int puerto = Integer.parseInt(Env.APP_PORT);
        Javalin app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte(templateEngine));
            for (HttpController controller : controllers) {
                controller.registerRoutes(config);
            }
            // // SINTAXIS JAVALIN 7: Las rutas se definen directamente usando
            // 'config.routes'
            // config.routes.get("/", ctx -> {
            // ctx.render("index.jte", Collections.singletonMap("usuario",
            // "Desarrollador"));
            // });

            // config.routes.get("/api/estado", ctx -> {
            // ctx.json(Collections.singletonMap("estado", "Servidor operativo en hilos
            // virtuales"));
            // });

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
