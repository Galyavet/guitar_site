CREATE TABLE users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username   VARCHAR(255)                            NOT NULL,
    email      VARCHAR(255)                            NOT NULL,
    password   VARCHAR(255)                            NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    role_id    BIGINT                                  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);