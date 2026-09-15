package es.maneldevs.infraestructure.in.adapter.web;

import java.util.Map;

import es.maneldevs.infraestructure.in.adapter.HttpController;
import es.maneldevs.infraestructure.in.model.UserSession;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class DeshboardWebController implements HttpController {

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.get("/web/dashboard", this::dashboard);
    }

    private void dashboard(Context ctx) {
        UserSession userLogged = ctx.attribute("userLogged");
        var model = Map.of("title", "Dashboard", "userEmail", userLogged.email());
        ctx.render("dashboard.jte", model);
    }
    
}
