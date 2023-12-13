CREATE TABLE IF NOT EXISTS sensors
(
    id          UUID PRIMARY KEY,
    name        VARCHAR UNIQUE NOT NULL,
    sensor_info VARCHAR
);