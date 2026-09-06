package es.maneldevs.infraestructure.in.adapter;

import io.javalin.config.JavalinConfig;

public interface HttpController {
    void registerRoutes(JavalinConfig config);
}
