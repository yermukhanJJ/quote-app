CREATE TABLE users_roles (
    users_id bigint NOT NULL,
    roles_id bigint NOT NULL,

    FOREIGN KEY(users_id)
    REFERENCES users (id),

    FOREIGN KEY(roles_id)
    REFERENCES roles (id)
);