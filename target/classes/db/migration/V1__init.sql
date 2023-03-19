CREATE TABLE users (
    id serial,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled boolean NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE authorities (
    id_user bigint,
    authority varchar(50) NOT NULL,

    CONSTRAINT authorities_idx UNIQUE (authority),

    CONSTRAINT authorities_ibfk_1
    FOREIGN KEY (id_user)
    REFERENCES users (id)
);

CREATE TABLE quote (
    id serial,
    title varchar(255) NOT NULL,
    content varchar(1000) NOT NULL,
    score bigint,
    id_user bigint NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (id_user)
    REFERENCES users (id)

);