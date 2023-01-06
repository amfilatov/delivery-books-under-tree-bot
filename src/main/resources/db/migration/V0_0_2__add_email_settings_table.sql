CREATE SEQUENCE IF NOT EXISTS email_settings_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE email_settings
(
    user_id         BIGINT NOT NULL,
    kindle_email    VARCHAR(1024),
    sender_email    VARCHAR(1024),
    sender_password VARCHAR(1024),
    CONSTRAINT pk_email_settings PRIMARY KEY (user_id)
);

ALTER TABLE email_settings
    ADD CONSTRAINT FK_EMAIL_SETTINGS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);