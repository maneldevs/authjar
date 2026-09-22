ALTER TABLE client_apps ADD COLUMN api_key_hash VARCHAR(255);

CREATE UNIQUE INDEX IF NOT EXISTS idx_client_apps_api_key_hash ON client_apps (api_key_hash);

DROP TABLE IF EXISTS api_keys;
