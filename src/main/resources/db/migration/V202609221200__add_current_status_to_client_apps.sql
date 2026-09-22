ALTER TABLE client_apps ADD COLUMN current_status BOOLEAN;

ALTER TABLE client_apps ADD COLUMN current_status_data JSONB;

ALTER TABLE client_apps ADD COLUMN current_status_datetime TIMESTAMPTZ;
