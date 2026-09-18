DROP INDEX IF EXISTS idx_client_apps_app_id_client_id;

ALTER TABLE client_apps ADD COLUMN name VARCHAR(255);

CREATE UNIQUE INDEX IF NOT EXISTS idx_client_apps_name ON client_apps (name);