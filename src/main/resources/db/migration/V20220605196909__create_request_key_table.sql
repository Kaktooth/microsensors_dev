CREATE TABLE IF NOT EXISTS key_requests
(
    id           UUID PRIMARY KEY,
    request_date TIMESTAMP NOT NULL,
    key_id       UUID      NOT NULL REFERENCES keys (id) ON DELETE CASCADE
);
