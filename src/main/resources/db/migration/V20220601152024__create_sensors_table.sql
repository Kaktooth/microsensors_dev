CREATE TABLE IF NOT EXISTS sensors
(
    id         UUID PRIMARY KEY,
    personal   BOOLEAN NOT NULL,
    country_id UUID    NOT NULL REFERENCES countries (id),
    user_id    UUID    NOT NULL REFERENCES users (id)
);