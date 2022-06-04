CREATE TABLE sensor
(
    id          UUID PRIMARY KEY,
    name        VARCHAR UNIQUE NOT NULL,
    sensor_info VARCHAR
);