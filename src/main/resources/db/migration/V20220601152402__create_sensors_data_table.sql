CREATE TABLE sensor_data
(
    id           UUID PRIMARY KEY,
    receive_date TIMESTAMP   NOT NULL,
    data         BYTEA       NOT NULL,
    sensor_id    UUID NOT NULL REFERENCES sensor (id) ON DELETE CASCADE
);