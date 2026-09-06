package es.maneldevs.infraestructure.in.adapter.web;

import java.util.Map;

import org.eclipse.jetty.http.HttpCookie.SameSite;

import es.maneldevs.application.portin.AuthUseCase;
import es.maneldevs.infraestructure.config.Env;
import es.maneldevs.infraestructure.in.adapter.HttpController;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.Cookie;

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
        if (user == null) {
            ctx.redirect("/web/login?error=true"); // TODO aquí se puede poner una cookie
            return;
        }
        Session session = authUseCase.generateSession(user);
        ctx.cookie(new Cookie("session_id", session.getId(), "/", Env.SESSION_DURATION_IN_DAYS * 60 * 60 * 24, true,
                true, SameSite.LAX));
        ctx.redirect("/web");
    }

}
