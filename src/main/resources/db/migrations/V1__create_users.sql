CREATE TABLE IF NOT EXISTS users
(
    user_id  UUID         NOT NULL,
    username VARCHAR(20)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

ALTER TABLE users ADD CONSTRAINT uc_users_username UNIQUE (username);
