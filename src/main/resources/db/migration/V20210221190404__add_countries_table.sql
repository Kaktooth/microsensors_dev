CREATE TABLE IF NOT EXISTS countries
(
--     id   SERIAL PRIMARY KEY,
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR UNIQUE NOT NULL,
    code VARCHAR UNIQUE NOT NULL
);
