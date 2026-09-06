package es.maneldevs.infraestructure.config;

import java.util.Set;

import es.maneldevs.infraestructure.in.model.RoleEnum;
import es.maneldevs.infraestructure.in.model.UserSession;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.RouteRole;

public class AccessManager {

    public static void manageAccess(Context ctx) {
        Set<RouteRole> routeRoles = ctx.routeRoles();
        if (routeRoles.isEmpty()) {
            return;
        }
        UserSession userLogged = ctx.attribute("userLogged");
        if (userLogged == null) {
            raiseError(ctx);
            return;
        }
        RoleEnum userRole = userLogged.role();
        if (!routeRoles.contains(userRole)) {
            raiseError(ctx);
        }
    }

    private static void raiseError(Context ctx) {
        if (ctx.path().startsWith("/api")) {
            ctx.status(401).result("Unauthorized");
        } else {
            ctx.redirect("/web/login");
            throw new UnauthorizedResponse("Unauthorized");
        }
    }
}
