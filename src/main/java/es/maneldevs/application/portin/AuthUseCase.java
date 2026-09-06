package es.maneldevs.application.portin;

import es.maneldevs.infraestructure.in.model.UserSession;

public interface AuthUseCase {
    User getUserLoggedFromSession(String sessionId);

    User getUserLoggedFromToken(String token);

    boolean apiKeyIsValid(String apiKey);

    User authenticate(String email, String password);

    String generateToken(String user);

    Session generateSession(User user);
}
