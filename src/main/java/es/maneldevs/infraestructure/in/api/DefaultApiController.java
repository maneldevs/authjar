package es.maneldevs.infraestructure.in.api;

import java.util.Collections;

import es.maneldevs.infraestructure.in.HttpController;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class DefaultApiController implements HttpController {

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.get("/api/health", this::health);
    }

    public void health(Context ctx) {
        ctx.json(Collections.singletonMap("estado", "Servidor operativo en hilos virtuales"));
    }
}
