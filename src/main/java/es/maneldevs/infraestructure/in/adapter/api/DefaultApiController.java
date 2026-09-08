package es.maneldevs.infraestructure.in.adapter.api;

import java.util.Map;

import es.maneldevs.infraestructure.in.adapter.HttpController;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class DefaultApiController implements HttpController {

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.get("/api/health", this::health);
        config.routes.get("/api/protected", this::checkProtected);
    }

    private void health(Context ctx) {
        ctx.json(Map.of("state", "OK"));
    }

    private void checkProtected(Context ctx) {
        ctx.json(Map.of("state", "OK"));
    }
}
