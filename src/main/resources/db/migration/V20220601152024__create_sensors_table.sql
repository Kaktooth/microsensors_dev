CREATE TABLE sensors
(
    id          UUID PRIMARY KEY,
    name        VARCHAR UNIQUE NOT NULL,
    sensor_info VARCHAR
);