CREATE TABLE IF NOT EXISTS keys
(
    id            UUID PRIMARY KEY,
    creation_date TIMESTAMP      NOT NULL,
    key           UUID           NOT NULL,
    ip            VARCHAR UNIQUE NOT NULL

);
