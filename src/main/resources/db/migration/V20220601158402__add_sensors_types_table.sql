CREATE TABLE IF NOT EXISTS sensors_types
(
    sensor_id  UUID
        CONSTRAINT fk_sensor_id REFERENCES sensors (id) ON DELETE CASCADE,
    sensor_type_id UUID
        CONSTRAINT fk_sensor_type_id REFERENCES sensor_types (id),
    PRIMARY KEY (sensor_id, sensor_type_id)
);