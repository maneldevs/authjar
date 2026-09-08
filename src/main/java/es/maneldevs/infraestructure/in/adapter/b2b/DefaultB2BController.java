package es.maneldevs.infraestructure.in.adapter.b2b;

import es.maneldevs.infraestructure.in.adapter.HttpController;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class DefaultB2BController implements HttpController {

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.get("/b2b/health", this::health);
        config.routes.get("/b2b/protected", this::checkProtected);
    }

    private void health(Context ctx) {
        ctx.json(java.util.Map.of("state", "OK"));
    }
    
    private void checkProtected(Context ctx) {
        ctx.json(java.util.Map.of("state", "OK"));
    }
}
