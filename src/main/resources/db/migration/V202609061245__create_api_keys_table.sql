CREATE TABLE IF NOT EXISTS api_keys (
    id VARCHAR(255) PRIMARY KEY,
    api_key_hash VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    client_app_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_api_keys_client_apps FOREIGN KEY (client_app_id) REFERENCES client_apps (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_api_keys_api_key_hash ON api_keys (api_key_hash);