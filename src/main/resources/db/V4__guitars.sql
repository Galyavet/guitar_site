CREATE TABLE guitars
(
    id          BIGINT                      NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    description TEXT,
    image_url   VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    added_by_id BIGINT,
    CONSTRAINT pk_guitar PRIMARY KEY (id)
);

ALTER TABLE guitars
    ADD CONSTRAINT FK_GUITAR_ON_ADDEDBY FOREIGN KEY (added_by_id) REFERENCES users (id);