package es.maneldevs.infraestructure.in.adapter.web;

import java.util.Map;

import es.maneldevs.application.portin.ClientUseCase;
import es.maneldevs.domain.model.Client;
import es.maneldevs.infraestructure.in.adapter.HttpController;
import es.maneldevs.infraestructure.in.model.UserSession;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class ClientWebController implements HttpController {
    private final ClientUseCase clientUseCase;

    public ClientWebController(ClientUseCase clientUseCase) {
        this.clientUseCase = clientUseCase;
    }

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.get("/web/clients", this::listClients);
        config.routes.get("/web/clients/new", this::newClientForm);
        config.routes.post("/web/clients", this::createClient);
        config.routes.get("/web/clients/{id}/edit", this::editClientForm);
        config.routes.post("/web/clients/{id}/edit", this::updateClient);
    }

    private void listClients(Context ctx) {
        UserSession userLogged = ctx.attribute("userLogged");
        var clients = clientUseCase.getClients();
        var model = Map.of("userEmail", userLogged.email(), "clients", clients);
        ctx.render("clients.jte", model);
    }

    private void newClientForm(Context ctx) {
        UserSession userLogged = ctx.attribute("userLogged");
        String error = FlashMessages.readError(ctx);
        var model = Map.of("userEmail", userLogged.email(), "error", error);
        ctx.render("clients-new.jte", model);
    }

    private void createClient(Context ctx) {
        String name = ctx.formParam("name");
        clientUseCase.createClient(name);
        ctx.redirect("/web/clients");
    }

    private void editClientForm(Context ctx) {
        UserSession userLogged = ctx.attribute("userLogged");
        String id = ctx.pathParam("id");
        String error = FlashMessages.readError(ctx);
        Client client = clientUseCase.getClientById(id);
        var model = Map.of("userEmail", userLogged.email(), "client", client, "error", error);
        ctx.render("clients-edit.jte", model);
    }

    private void updateClient(Context ctx) {
        String id = ctx.pathParam("id");
        String name = ctx.formParam("name");
        clientUseCase.updateClient(id, name);
        ctx.redirect("/web/clients");
    }

}
