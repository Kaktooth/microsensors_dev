CREATE TABLE IF NOT EXISTS sensor_types
(
--     id   SERIAL PRIMARY KEY,
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR UNIQUE NOT NULL
);
