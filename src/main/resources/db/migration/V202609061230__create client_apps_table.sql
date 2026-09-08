CREATE TABLE IF NOT EXISTS client_apps (
    id VARCHAR(255) PRIMARY KEY,
    company VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX IF NOT EXISTS idx_client_apps_active ON client_apps (id) WHERE active = TRUE;