package es.maneldevs.infraestructure.in.adapter.web;

import java.util.Map;

import es.maneldevs.application.portin.AppUseCase;
import es.maneldevs.domain.model.App;
import es.maneldevs.infraestructure.in.adapter.HttpController;
import es.maneldevs.infraestructure.in.model.UserSession;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class AppWebController implements HttpController {
    private final AppUseCase appUseCase;

    public AppWebController(AppUseCase appUseCase) {
        this.appUseCase = appUseCase;
    }

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.get("/web/apps", this::listApps);
        config.routes.get("/web/apps/new", this::newAppForm);
        config.routes.post("/web/apps", this::createApp);
        config.routes.get("/web/apps/{id}/edit", this::editAppForm);
        config.routes.post("/web/apps/{id}/edit", this::updateApp);
    }

    private void listApps(Context ctx) {
        UserSession userLogged = ctx.attribute("userLogged");
        var apps = appUseCase.getClients();
        var model = Map.of("userEmail", userLogged.email(), "apps", apps);
        ctx.render("apps.jte", model);
    }

    private void newAppForm(Context ctx) {
        UserSession userLogged = ctx.attribute("userLogged");
        String error = FlashMessages.readError(ctx);
        var model = Map.of("userEmail", userLogged.email(), "error", error);
        ctx.render("apps-new.jte", model);
    }

    private void createApp(Context ctx) {
        String name = ctx.formParam("name");
        appUseCase.createClient(name);
        ctx.redirect("/web/apps");
    }

    private void editAppForm(Context ctx) {
        UserSession userLogged = ctx.attribute("userLogged");
        String id = ctx.pathParam("id");
        String error = FlashMessages.readError(ctx);
        App app = appUseCase.getClientById(id);
        var model = Map.of("userEmail", userLogged.email(), "app", app, "error", error);
        ctx.render("apps-edit.jte", model);
    }

    private void updateApp(Context ctx) {
        String id = ctx.pathParam("id");
        String name = ctx.formParam("name");
        appUseCase.updateClient(id, name);
        ctx.redirect("/web/apps");
    }
    
}
