DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id       IDENTITY PRIMARY KEY,
    name     VARCHAR(100),
    email    VARCHAR(255),
    password VARCHAR(100)
);

