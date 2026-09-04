package es.maneldevs.infraestructure.in;

import io.javalin.config.JavalinConfig;

public interface HttpController {
    void registerRoutes(JavalinConfig config);
}
