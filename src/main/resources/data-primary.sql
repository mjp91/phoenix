INSERT INTO config (id, license_authentication_token)
VALUES (1, '4oep-5ccKh4asEG3lVoE7wWOpr7-m-DS');

INSERT INTO role (id, name)
VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name)
VALUES (2, 'ROLE_ADMIN');
INSERT INTO role (id, name)
VALUES (3, 'ROLE_SUPER_ADMIN');

INSERT INTO "user" (id, username, password, full_name, email, calendar_token, credentials_expired, ldap, totp_enabled,
                    enabled)
VALUES (1, 'buzz', NULL, 'Buzz Lightyear', 'buzz@example.com', '6753a413-2c05-4083-8671-2c9d9ddb2db3', FALSE, TRUE,
        FALSE, TRUE),
       (2, 'matt', NULL, 'Matthew Damon', 'matt@example.com', '85171ffc-a233-449b-8629-f6a020d010db', FALSE, TRUE,
        FALSE, TRUE),
       (3, 'rick', '$2a$10$uWk/CVVsNl1xSUUToSHGA.0DImPXfyG5ZH7NG3mvCf4mFGRX4Q.Ou', 'Rick Sanchez',
        'rick@example.com',
        '10702fa0-74e6-4d17-851b-5c6ae06c917c', FALSE, FALSE, TRUE, TRUE);

INSERT INTO user_roles
VALUES (1, 2),
       (2, 1),
       (3, 3);

ALTER SEQUENCE hibernate_sequence RESTART WITH 5;
