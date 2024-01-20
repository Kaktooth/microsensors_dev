CREATE TABLE IF NOT EXISTS sensors_placements
(
    sensor_id  UUID
        CONSTRAINT fk_sensor_id REFERENCES sensors (id) ON DELETE CASCADE,
    placement_id UUID
        CONSTRAINT fk_placement_id REFERENCES placements (id),
    PRIMARY KEY (sensor_id, placement_id)
);