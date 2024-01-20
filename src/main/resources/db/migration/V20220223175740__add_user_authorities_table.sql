CREATE TABLE user_authorities
(
    id          INTEGER PRIMARY KEY,
    authorities VARCHAR NOT NULL
);

INSERT INTO user_authorities VALUES (0, 'USER'),   (1, 'ADMIN');