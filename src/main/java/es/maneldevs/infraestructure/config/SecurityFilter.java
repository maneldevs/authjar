package es.maneldevs.infraestructure.config;

import java.util.Set;

import es.maneldevs.application.portin.AuthUseCase;
import es.maneldevs.infraestructure.in.model.UserSession;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;

public class SecurityFilter {
    private static final Set<String> PUBLIC_API_ROUTES = Set.of("/api/health");
    private static final Set<String> PUBLIC_WEB_ROUTES = Set.of("/web/health", "/web/login");
    private static final Set<String> PUBLIC_B2B_ROUTES = Set.of("/b2b/health");

    private final AuthUseCase authUseCase;

    public SecurityFilter(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    public void doFilter(Context ctx) {
        UserSession userLogged = null;
        String path = ctx.path();
        if (isPublicRoute(path) || isStaticResource(path)) {
            return;
        }
        if (path.startsWith("/api")) {
            userLogged = validateApi(ctx);
        } else if (path.startsWith("/web")) {
            userLogged = validateSession(ctx);
        } else if (path.startsWith("/b2b")) {
            validarB2B(ctx);
        } else {
            ctx.status(404);
        }

        if (userLogged != null) {
            ctx.attribute("userLogged", userLogged);
        }
    }

    private UserSession validateApi(Context ctx) {
        // validate with cookie for request from jte templates
        String sessionId = ctx.cookie("session_id");
        if (sessionId != null && !sessionId.isBlank()) {
            return validateSession(ctx);
        }
        // validate with header for request from rest api
        return validateToken(ctx);
    }

    private UserSession validateSession(Context ctx) {
        String sessionId = ctx.cookie("session_id");
        if (sessionId != null && !sessionId.isBlank()) {
            User userLogged = authUseCase.getUserLoggedFromSession(sessionId);
            UserSession userSession = new UserSession(userLogged.id(), userLogged.email(), userLogged.role());
            if (userLogged != null) {
                return userLogged;
            }
        }
        ctx.redirect("/web/login");
        throw new UnauthorizedResponse("Invalid session");
    }

    private UserSession validateToken(Context ctx) {
        String authHeader = ctx.header("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            User userLogged = authUseCase.getUserLoggedFromToken(token);
            UserSession userSession = new UserSession(userLogged.id(), userLogged.email(), userLogged.role());
            if (userLogged != null) {
                return userLogged;
            }
        }
        throw new UnauthorizedResponse("Invalid token or session");
    }

    private void validarB2B(Context ctx) {
        String apiKey = ctx.header("X-API-KEY");
        if (!authUseCase.apiKeyIsValid(apiKey)) {
            throw new UnauthorizedResponse("Invalid API KEY");
        }
    }

    private boolean isStaticResource(String path) {
        return path.startsWith("/static");
    }

    private boolean isPublicRoute(String path) {
        return PUBLIC_API_ROUTES.contains(path) || PUBLIC_WEB_ROUTES.contains(path) || PUBLIC_B2B_ROUTES.contains(path);
    }

}
