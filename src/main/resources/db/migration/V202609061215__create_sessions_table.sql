CREATE TABLE IF NOT EXISTS sessions (
    id VARCHAR(255) PRIMARY KEY,
    expires_at TIMESTAMP NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_sessions_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_sessions_validation ON sessions (id, expires_at);
