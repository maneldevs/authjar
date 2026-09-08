package es.maneldevs.application.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import es.maneldevs.application.portin.AuthUseCase;
import es.maneldevs.application.portout.ApiKeyPort;
import es.maneldevs.application.portout.UserPort;
import es.maneldevs.domain.exception.InvalidCredentialsException;
import es.maneldevs.domain.model.Session;
import es.maneldevs.domain.model.User;
import es.maneldevs.infraestructure.config.Env;

public class AuthService implements AuthUseCase {
    private final UserPort userPort;
    private final ApiKeyPort apiKeyPort;

    public AuthService(UserPort userPort, ApiKeyPort apiKeyPort) {
        this.userPort = userPort;
        this.apiKeyPort = apiKeyPort;
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
    public boolean apiKeyIsValid(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            return false;
        }
        String hashedApiKey = generateHashSha256(apiKey);
        return apiKeyPort.existsActiveApiKey(hashedApiKey);
    }

    @Override
    public User authenticate(String email, String password) {
        User user = userPort.getUserByEmail(email);
        if (user == null || !user.getPasswordHash().equals(generateHashSha256(password))) {
            throw new InvalidCredentialsException();
        }
        return user;
    }

    @Override
    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getId())
                .withIssuer(Env.JWL_ISSUER)
                .sign(Algorithm.HMAC256(Env.JWT_SECRET));
    }

    @Override
    public Session generateSession(User user) {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        String sessionId = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        Instant expiresAt = Instant.now().plus(Env.SESSION_DURATION_IN_DAYS, ChronoUnit.DAYS);
        return new Session(sessionId, user, expiresAt);
    }

    private String generateHashSha256(String text) {
        try {
            byte[] hashBytes = MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

}
