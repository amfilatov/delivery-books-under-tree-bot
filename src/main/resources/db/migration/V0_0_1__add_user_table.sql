CREATE TABLE users
(
    id         BIGINT        NOT NULL,
    first_name VARCHAR(1024),
    last_name  VARCHAR(1024),
    user_name  VARCHAR(1024) NOT NULL,
    bot_state  VARCHAR(255)  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);