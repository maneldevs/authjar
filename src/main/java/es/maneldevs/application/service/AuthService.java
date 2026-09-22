package es.maneldevs.application.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import es.maneldevs.application.portin.AuthUseCase;
import es.maneldevs.application.portout.ClientAppPort;
import es.maneldevs.application.portout.SessionPort;
import es.maneldevs.application.portout.UserPort;
import es.maneldevs.domain.exception.InvalidCredentialsException;
import es.maneldevs.domain.model.ClientApp;
import es.maneldevs.domain.model.Session;
import es.maneldevs.domain.model.User;
import es.maneldevs.infraestructure.config.Env;

public class AuthService implements AuthUseCase {
    private final UserPort userPort;
    private final ClientAppPort clientAppPort;
    private final SessionPort sessionPort;
    private final Argon2 argon2;

    public AuthService(UserPort userPort, ClientAppPort clientAppPort, SessionPort sessionPort) {
        this.userPort = userPort;
        this.clientAppPort = clientAppPort;
        this.sessionPort = sessionPort;
        this.argon2 = Argon2Factory.create();
    }

    @Override
    public User getUserLoggedFromSessionNotExpired(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            return null;
        }
        return userPort.getUserLoggedFromSessionNotExpired(sessionId);
    }

    @Override
    public User getUserLoggedFromToken(String token) {
        try {
            if (token == null || token.isBlank()) {
                return null;
            }
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(Env.JWT_SECRET))
                    .withIssuer(Env.JWL_ISSUER)
                    .build()
                    .verify(token);
            String userId = jwt.getSubject();
            return userPort.getActiveUserById(userId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ClientApp authenticateB2B(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            return null;
        }
        String hashedApiKey = ApiKeyUtils.hash(apiKey);
        return clientAppPort.getActiveClientAppByApiKeyHash(hashedApiKey);
    }

    @Override
    public String authenticateApi(String email, String password) {
        User user = authenticate(email, password);
        return generateToken(user);
    }

    @Override
    public Session authenticateWeb(String email, String password) {
        User user = authenticate(email, password);
        return generateSession(user);
    }
    
    @Override
    public User registerUser(String email, String password, String role) {
        String passwordHash = generateHashArgon2(password);
        return userPort.createUser(email, passwordHash, role);
    }

    private String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getId())
                .withIssuer(Env.JWL_ISSUER)
                .sign(Algorithm.HMAC256(Env.JWT_SECRET));
    }

    private Session generateSession(User user) {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        String sessionId = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        Instant expiresAt = Instant.now().plus(Env.SESSION_DURATION_IN_DAYS, ChronoUnit.DAYS);
        Session session = new Session(sessionId, user, expiresAt);
        sessionPort.saveSession(session);
        return session;
    }


    private String generateHashArgon2(String text) {
        return argon2.hash(2, 65536, 1, text.toCharArray());
    }

        private User authenticate(String email, String password) {
        User user = userPort.getUserByEmail(email);
        if (user == null || !user.isActive() || !argon2.verify(user.getPasswordHash(), password.toCharArray())) {
            throw new InvalidCredentialsException();
        }
        return user;
    }

}
