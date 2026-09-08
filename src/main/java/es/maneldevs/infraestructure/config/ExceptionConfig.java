package es.maneldevs.infraestructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.maneldevs.domain.exception.AccessDeniedException;
import es.maneldevs.domain.exception.InvalidCredentialsException;
import es.maneldevs.domain.exception.UnauthenticatedException;
import io.javalin.config.JavalinConfig;

public class ExceptionConfig {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionConfig.class);

    public static void init(JavalinConfig config) {
        config.routes.exception(InvalidCredentialsException.class, (e, ctx) -> {
            if (ctx.path().startsWith("/web")) {
                ctx.redirect("/web/login?error=true");
            } else {
                ctx.status(401).result("Unauthorized");
            }
        });
        config.routes.exception(UnauthenticatedException.class, (e, ctx) -> {
            if (ctx.path().startsWith("/web")) {
                ctx.redirect("/web/login");
            } else {
                ctx.status(401).result("Unauthorized");
            }
        });
        config.routes.exception(AccessDeniedException.class, (e, ctx) -> {
            if (ctx.path().startsWith("/web")) {
                ctx.redirect("/web/login");
            } else {
                ctx.status(403).result("Forbidden");
            }
        });
        config.routes.exception(Exception.class, (e, ctx) -> {
            logger.error("Unhandled exception processing request {}", ctx.path(), e);
            ctx.status(500).result("Internal Server Error");
        });
    }
}
