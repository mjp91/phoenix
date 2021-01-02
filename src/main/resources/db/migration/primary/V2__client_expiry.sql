ALTER TABLE client
    ADD COLUMN expiry timestamp NOT NULL DEFAULT NOW();
