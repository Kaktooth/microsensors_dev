CREATE TABLE IF NOT EXISTS authorities
(
    id        UUID PRIMARY KEY,
    username  VARCHAR NOT NULL,
    authority INTEGER NOT NULL REFERENCES user_authorities (id),
    user_id   UUID
        CONSTRAINT fk_user_authorities REFERENCES users (id) ON DELETE CASCADE
);
create unique index ix_auth_account on authorities (username, authority);