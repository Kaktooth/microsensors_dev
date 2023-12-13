CREATE TABLE IF NOT EXISTS sensor_messages
(
    id           UUID PRIMARY KEY,
    receive_date TIMESTAMP NOT NULL,
    message      VARCHAR   NOT NULL,
    sensor_id    UUID      NOT NULL REFERENCES sensors (id) ON DELETE CASCADE
);
