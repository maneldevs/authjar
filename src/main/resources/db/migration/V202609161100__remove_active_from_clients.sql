DROP INDEX IF EXISTS idx_clients_active;

ALTER TABLE clients DROP COLUMN IF EXISTS active;