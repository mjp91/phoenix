INSERT INTO "role" (id, name)
VALUES (NEXTVAL('hibernate_sequence'), 'ROLE_USER'),
       (NEXTVAL('hibernate_sequence'), 'ROLE_ADMIN'),
       (NEXTVAL('hibernate_sequence'), 'ROLE_SUPER_ADMIN');
