package es.maneldevs.infraestructure.config;

import java.util.Set;

import es.maneldevs.domain.exception.AccessDeniedException;
import es.maneldevs.domain.exception.UnauthenticatedException;
import es.maneldevs.infraestructure.in.model.RoleEnum;
import es.maneldevs.infraestructure.in.model.UserSession;
import io.javalin.http.Context;
import io.javalin.security.RouteRole;

public class AccessManager {

    public static void manageAccess(Context ctx) {
        Set<RouteRole> routeRoles = ctx.routeRoles();
        if (routeRoles.isEmpty()) {
            return;
        }
        UserSession userLogged = ctx.attribute("userLogged");
        if (userLogged == null) {
            throw new UnauthenticatedException();
        }
        RoleEnum userRole = userLogged.role();
        if (!routeRoles.contains(userRole)) {
            throw new AccessDeniedException();
        }
    }
}
