DROP TABLE authorities;

CREATE TABLE roles (
    id serial,
    name varchar(20) NOT NULL,

    PRIMARY KEY(id)
);

INSERT INTO roles (id,name) VALUES (1,'USER');
INSERT INTO roles (id,name) VALUES (2,'ADMIN');