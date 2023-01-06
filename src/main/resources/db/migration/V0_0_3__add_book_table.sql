CREATE SEQUENCE IF NOT EXISTS email_settings_gen START WITH 1 INCREMENT BY 50;

CREATE TABLE books
(
    id             BIGINT                      NOT NULL,
    file_unique_id VARCHAR(255)                NOT NULL,
    file_id        VARCHAR(255)                NOT NULL,
    file_name      VARCHAR(255)                NOT NULL,
    mimi_type      VARCHAR(255)                NOT NULL,
    file_size      BIGINT                      NOT NULL,
    user_id        BIGINT,
    created        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    state          VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_books PRIMARY KEY (id)
);

ALTER TABLE books
    ADD CONSTRAINT FK_BOOKS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);