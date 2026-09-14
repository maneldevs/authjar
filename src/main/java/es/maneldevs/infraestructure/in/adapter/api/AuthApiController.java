package es.maneldevs.infraestructure.in.adapter.api;

import es.maneldevs.application.portin.AuthUseCase;
import es.maneldevs.infraestructure.in.adapter.HttpController;
import es.maneldevs.infraestructure.in.model.LoginApiResponse;
import es.maneldevs.infraestructure.in.model.LoginRequest;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class AuthApiController implements HttpController {
    private final AuthUseCase authUseCase;

    public AuthApiController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @Override
    public void registerRoutes(JavalinConfig config) {
        config.routes.post("/api/auth/token", this::login);
    }

    private void login(Context ctx) {
        LoginRequest loginRequest = ctx.bodyAsClass(LoginRequest.class);
        String token = authUseCase.authenticateApi(loginRequest.email(), loginRequest.password());
        ctx.status(200).json(new LoginApiResponse(token, "Bearer"));
    }

}
