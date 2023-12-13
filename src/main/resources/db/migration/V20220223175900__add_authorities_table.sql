CREATE TABLE IF NOT EXISTS authorities
(
    id        SERIAL  NOT NULL,
    username  VARCHAR NOT NULL,
    email     VARCHAR NOT NULL,
    authority VARCHAR NOT NULL,
    user_id   UUID CONSTRAINT fk_user_authorities REFERENCES users (id) ON DELETE CASCADE
);
create unique index ix_auth_account on authorities (username, email, authority);