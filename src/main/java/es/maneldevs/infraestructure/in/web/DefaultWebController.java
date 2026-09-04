package es.maneldevs.infraestructure.in.web;

import java.util.Collections;

import es.maneldevs.infraestructure.in.HttpController;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class DefaultWebController implements HttpController {

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.get("/web/health", this::health);
    }

    public void health(Context ctx) {
        var modelo = Collections.singletonMap("titulo", "Iniciar Sesión");
        ctx.render("health.jte", modelo);
    }

}
