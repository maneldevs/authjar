CREATE TABLE IF NOT EXISTS apps (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_apps_name ON apps (name);

ALTER TABLE client_apps ADD COLUMN app_id VARCHAR(255);

ALTER TABLE client_apps
    ADD CONSTRAINT fk_client_apps_apps FOREIGN KEY (app_id) REFERENCES apps (id) ON DELETE CASCADE;

ALTER TABLE client_apps DROP COLUMN app_name;

CREATE UNIQUE INDEX IF NOT EXISTS idx_client_apps_app_id_client_id ON client_apps (app_id, client_id);
