package es.maneldevs.infraestructure.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.maneldevs.domain.exception.AccessDeniedException;
import es.maneldevs.domain.exception.InvalidCredentialsException;
import es.maneldevs.domain.exception.UnauthenticatedException;
import es.maneldevs.domain.exception.ValidationException;
import es.maneldevs.infraestructure.in.adapter.web.FlashMessages;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.HttpStatus;
import io.javalin.router.exception.HttpResponseExceptionMapper;

public class ExceptionConfig {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionConfig.class);

    public static void init(JavalinConfig config) {
        config.routes.exception(InvalidCredentialsException.class, (e, ctx) -> {
            if (ctx.path().startsWith("/web")) {
                ctx.redirect("/web/login?error=true");
            } else {
                sendJsonError(ctx, 401, "Unauthorized", e.getMessage());
            }
        });
        config.routes.exception(UnauthenticatedException.class, (e, ctx) -> {
            if (ctx.path().startsWith("/web")) {
                ctx.redirect("/web/login");
            } else {
                sendJsonError(ctx, 401, "Unauthorized", e.getMessage());
            }
        });
        config.routes.exception(AccessDeniedException.class, (e, ctx) -> {
            if (ctx.path().startsWith("/web")) {
                ctx.redirect("/web/login");
            } else {
                sendJsonError(ctx, 403, "Forbidden", e.getMessage());
            }
        });
        config.routes.exception(ValidationException.class, (e, ctx) -> {
            if (ctx.path().startsWith("/web")) {
                String formPath = ctx.path().equals("/web/clients") ? "/web/clients/new" : ctx.path();
                FlashMessages.setError(ctx, e.getMessage());
                ctx.redirect(formPath);
            } else {
                sendJsonError(ctx, 422, "Unprocessable Entity", e.getMessage());
            }
        });
        config.routes.exception(HttpResponseException.class, (e, ctx) -> {
            if (ctx.path().startsWith("/web")) {
                HttpResponseExceptionMapper.INSTANCE.handle(e, ctx);
            } else {
                sendJsonError(ctx, e.getStatus(), HttpStatus.forStatus(e.getStatus()).getMessage(), e.getMessage());
            }
        });
        config.routes.exception(Exception.class, (e, ctx) -> {
            logger.error("Unhandled exception processing request {}", ctx.path(), e);
            if (ctx.path().startsWith("/web")) {
                ctx.status(500).result("Internal Server Error");
            } else {
                sendJsonError(ctx, 500, "Internal Server Error", e.getMessage());
            }
        });
    }

    private static void sendJsonError(Context ctx, int code, String type, String message) {
        ctx.status(code).json(Map.of("code", code, "type", type, "message", message != null ? message : type));
    }
}
