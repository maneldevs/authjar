package es.maneldevs.infraestructure.in.adapter.web;

import java.util.Collections;

import es.maneldevs.infraestructure.in.adapter.HttpController;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class DefaultWebController implements HttpController {

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.get("/web/health", this::health);
        config.routes.get("/web/protected", this::checkProtected);
    }

    private void health(Context ctx) {
        var modelo = Collections.singletonMap("titulo", "Iniciar Sesión");
        ctx.render("health.jte", modelo);
    }

    private void checkProtected(Context ctx) {
        var modelo = Collections.singletonMap("titulo", "Iniciar Sesión");
        ctx.render("health.jte", modelo);
    }

}
