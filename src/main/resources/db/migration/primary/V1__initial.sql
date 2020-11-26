CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

CREATE TABLE client (
    id                int8 NOT NULL,
    database_password varchar(255),
    database_user     varchar(255),
    jdbc_url          varchar(255),
    name              varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE config (
    id                           int8 NOT NULL,
    license_authentication_token varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE privilege (
    id   int8 NOT NULL,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE role (
    id   int8 NOT NULL,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE role_privileges (
    roles_id      int8 NOT NULL,
    privileges_id int8 NOT NULL
);

CREATE TABLE "user" (
    id                   int8    NOT NULL,
    calendar_token       varchar(255),
    credentials_expired  boolean NOT NULL,
    email                varchar(255),
    enabled              boolean NOT NULL,
    full_name            varchar(255),
    ldap                 boolean NOT NULL,
    password             varchar(255),
    password_reset_token varchar(255),
    totp_enabled         boolean NOT NULL,
    totp_secret          varchar(255),
    username             varchar(255),
    client_id            int8,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    users_id int8 NOT NULL,
    roles_id int8 NOT NULL
);

ALTER TABLE IF EXISTS client
    ADD CONSTRAINT UK_dn5jasds5r1j3ewo5k3nhwkkq UNIQUE (name);

ALTER TABLE IF EXISTS "user"
    ADD CONSTRAINT UK_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email);

ALTER TABLE IF EXISTS "user"
    ADD CONSTRAINT UK_6wovvdthmwt1cp3s8n2prfg8k UNIQUE (password_reset_token);

ALTER TABLE IF EXISTS "user"
    ADD CONSTRAINT UK_sb8bbouer5wak8vyiiy4pf2bx UNIQUE (username);

ALTER TABLE IF EXISTS role_privileges
    ADD CONSTRAINT FKas5s9i1itvr8tgocse4xmtwox
        FOREIGN KEY (privileges_id)
            REFERENCES privilege;

ALTER TABLE IF EXISTS role_privileges
    ADD CONSTRAINT FK9n2w8s3aw0yk00s4nmqvucw6b
        FOREIGN KEY (roles_id)
            REFERENCES role;

ALTER TABLE IF EXISTS "user"
    ADD CONSTRAINT FKrl8au09hfjd9742b89li2rb3
        FOREIGN KEY (client_id)
            REFERENCES client;

ALTER TABLE IF EXISTS user_roles
    ADD CONSTRAINT FKj9553ass9uctjrmh0gkqsmv0d
        FOREIGN KEY (roles_id)
            REFERENCES role;

ALTER TABLE IF EXISTS user_roles
    ADD CONSTRAINT FK7ecyobaa59vxkxckg6t355l86
        FOREIGN KEY (users_id)
            REFERENCES "user";
