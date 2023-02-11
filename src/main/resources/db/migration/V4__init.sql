ALTER TABLE quote DROP COLUMN enabled;

CREATE TABLE user_score (
    id serial,
    id_user bigint NOT NULL,
    id_quote bigint NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (id_user)
    REFERENCES users(id),

    FOREIGN KEY (id_quote)
    REFERENCES quote(id)

    );