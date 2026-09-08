package es.maneldevs.infraestructure.in.adapter.web;

import java.util.Map;

import es.maneldevs.application.portin.AuthUseCase;
import es.maneldevs.domain.model.Session;
import es.maneldevs.domain.model.User;
import es.maneldevs.infraestructure.config.Env;
import es.maneldevs.infraestructure.in.adapter.HttpController;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.Cookie;
import io.javalin.http.SameSite;

public class AuthWebController implements HttpController {
    private final AuthUseCase authUseCase;

    public AuthWebController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.get("/web/login", this::login);
        config.routes.post("/web/auth/login", this::processLogin);
    }

    private void login(Context ctx) {
        var model = Map.of("title", "Iniciar Sesión");
        ctx.render("login.jte", model);
    }

    private void processLogin(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        User user = authUseCase.authenticate(email, password);
        Session session = authUseCase.generateSession(user);
        ctx.cookie(new Cookie("session_id", session.id(), "/", Env.SESSION_DURATION_IN_DAYS * 60 * 60 * 24, true,
                true, null, SameSite.LAX));
        ctx.redirect("/web");
    }

}
