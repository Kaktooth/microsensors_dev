CREATE TABLE users
(
    id           UUID PRIMARY KEY,
    username     VARCHAR UNIQUE NOT NULL,
    password     VARCHAR        NOT NULL,
    enabled      BOOLEAN        NOT NULL,
    email        VARCHAR        NOT NULL
);
