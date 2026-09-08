package es.maneldevs.domain.model;

import java.time.Instant;

public record Session(String id, User user, Instant expiresAt) {
}
