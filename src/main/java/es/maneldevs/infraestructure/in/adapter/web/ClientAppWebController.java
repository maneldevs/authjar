package es.maneldevs.infraestructure.in.adapter.web;

import java.util.Map;

import es.maneldevs.application.portin.AppUseCase;
import es.maneldevs.application.portin.ClientAppUseCase;
import es.maneldevs.application.portin.ClientUseCase;
import es.maneldevs.domain.model.Client;
import es.maneldevs.domain.model.ClientApp;
import es.maneldevs.infraestructure.in.adapter.HttpController;
import es.maneldevs.infraestructure.in.model.UserSession;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class ClientAppWebController implements HttpController {
    private final ClientAppUseCase clientAppUseCase;
    private final ClientUseCase clientUseCase;
    private final AppUseCase appUseCase;

    public ClientAppWebController(ClientAppUseCase clientAppUseCase, ClientUseCase clientUseCase, AppUseCase appUseCase) {
        this.clientAppUseCase = clientAppUseCase;
        this.clientUseCase = clientUseCase;
        this.appUseCase = appUseCase;
    }

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.get("/web/clients/{clientId}/apps", this::listClientApps);
        config.routes.get("/web/clients/{clientId}/apps/new", this::newClientAppForm);
        config.routes.post("/web/clients/{clientId}/apps/generate-api-key", this::generateApiKey);
        config.routes.post("/web/clients/{clientId}/apps", this::createClientApp);
        config.routes.get("/web/clients/{clientId}/apps/{appId}/edit", this::editClientAppForm);
        config.routes.post("/web/clients/{clientId}/apps/{appId}/edit", this::updateClientApp);
    }

    private void listClientApps(Context ctx) {
        UserSession userLogged = ctx.attribute("userLogged");
        String clientId = ctx.pathParam("clientId");
        Client client = clientUseCase.getClientById(clientId);
        var clientApps = clientAppUseCase.getClientApps(clientId);
        var model = Map.of("userEmail", userLogged.email(), "client", client, "clientApps", clientApps);
        ctx.render("client-apps.jte", model);
    }

    private void newClientAppForm(Context ctx) {
        UserSession userLogged = ctx.attribute("userLogged");
        String clientId = ctx.pathParam("clientId");
        String error = FlashMessages.readError(ctx);
        Client client = clientUseCase.getClientById(clientId);
        var apps = appUseCase.getClients();
        var model = Map.of("userEmail", userLogged.email(), "client", client, "apps", apps, "error", error);
        ctx.render("client-apps-new.jte", model);
    }

    private void generateApiKey(Context ctx) {
        ctx.json(Map.of("apiKey", clientAppUseCase.generateApiKey()));
    }

    private void createClientApp(Context ctx) {
        String clientId = ctx.pathParam("clientId");
        String appId = ctx.formParam("appId");
        String name = ctx.formParam("name");
        String apiKey = ctx.formParam("apiKey");
        clientAppUseCase.createClientApp(clientId, appId, name, apiKey);
        ctx.redirect("/web/clients/" + clientId + "/apps");
    }

    private void editClientAppForm(Context ctx) {
        UserSession userLogged = ctx.attribute("userLogged");
        String clientId = ctx.pathParam("clientId");
        String appId = ctx.pathParam("appId");
        String error = FlashMessages.readError(ctx);
        Client client = clientUseCase.getClientById(clientId);
        ClientApp clientApp = clientAppUseCase.getClientApp(clientId, appId);
        var model = Map.of("userEmail", userLogged.email(), "client", client, "clientApp", clientApp, "error", error);
        ctx.render("client-apps-edit.jte", model);
    }

    private void updateClientApp(Context ctx) {
        String clientId = ctx.pathParam("clientId");
        String appId = ctx.pathParam("appId");
        String name = ctx.formParam("name");
        Boolean active = ctx.formParam("active") != null;
        String apiKey = ctx.formParam("apiKey");
        clientAppUseCase.updateClientApp(clientId, appId, name, active, apiKey);
        ctx.redirect("/web/clients/" + clientId + "/apps");
    }

}
