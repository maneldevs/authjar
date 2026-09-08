package es.maneldevs.application.portin;

import es.maneldevs.domain.model.Session;
import es.maneldevs.domain.model.User;

public interface AuthUseCase {
    User getUserLoggedFromSessionNotExpired(String sessionId);

    User getUserLoggedFromToken(String token);

    boolean apiKeyIsValid(String apiKey);

    User authenticate(String email, String password);

    String generateToken(User user);

    Session generateSession(User user);
}
