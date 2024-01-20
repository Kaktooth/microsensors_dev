CREATE TABLE IF NOT EXISTS keys
(
    id            UUID PRIMARY KEY,
    creation_date TIMESTAMP      NOT NULL,
    key           UUID           NOT NULL,
    user_id       UUID UNIQUE    NOT NULL REFERENCES users (id),
    ip            VARCHAR UNIQUE NOT NULL
);
