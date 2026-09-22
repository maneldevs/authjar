package es.maneldevs.application.portin;

import es.maneldevs.domain.model.ClientApp;
import es.maneldevs.domain.model.Session;
import es.maneldevs.domain.model.User;

public interface AuthUseCase {
    User getUserLoggedFromSessionNotExpired(String sessionId);

    User getUserLoggedFromToken(String token);

    ClientApp authenticateB2B(String apiKey);

    User registerUser(String email, String password, String role);

    Session authenticateWeb(String email, String password);

    String authenticateApi(String email, String password);
}
