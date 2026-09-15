CREATE TABLE IF NOT EXISTS clients (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX IF NOT EXISTS idx_clients_active ON clients (id) WHERE active = TRUE;

ALTER TABLE client_apps DROP COLUMN company;

ALTER TABLE client_apps ADD COLUMN client_id VARCHAR(255);

ALTER TABLE client_apps
    ADD CONSTRAINT fk_client_apps_clients FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE;
