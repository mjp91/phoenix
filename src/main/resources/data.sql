INSERT INTO company (id, name, default_holiday_entitlement_hours, monday_start, monday_end, created_date,
                     last_modified_date)
VALUES (1, 'Holibyte', '200.0', '09:00', '17:00', NOW(), NOW());

INSERT INTO role (id, name)
VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name)
VALUES (2, 'ROLE_ADMIN');

INSERT INTO user (id, username, full_name, email, calendar_token)
VALUES (1, 'buzz', 'Buzz Lightyear', 'buzz@example.com', '6753a413-2c05-4083-8671-2c9d9ddb2db3'),
       (2, 'matt', 'Matthew Pearsall', 'mjp91@live.co.uk', '85171ffc-a233-449b-8629-f6a020d010db');

INSERT INTO user_roles
VALUES (1, 2);
INSERT INTO user_roles
VALUES (2, 1);

INSERT INTO job_role (id, description, created_date, last_modified_date)
VALUES (1, 'HR Assistant', NOW(), NOW()),
       (2, 'Software Development Manger', NOW(), NOW()),
       (3, 'Software Developer', NOW(), NOW());

INSERT INTO employee (id, user_id, manager_id, monday_start, monday_end, created_date, last_modified_date, job_role_id)
VALUES (1, 1, NULL, '09:00', '17:00', NOW(), NOW(), 2),
       (2, 2, 1, '09:00', '17:00', NOW(), NOW(), 3);

INSERT INTO holiday_year (id, name, year_start, year_end, created_date, last_modified_date)
VALUES (1, '2019', '2019-01-01', '2019-12-31', NOW(), NOW());

INSERT INTO holiday_entitlement (id, employee_id, holiday_year_id, holiday_entitlement_hours, created_date,
                                 last_modified_date)
VALUES (1, 1, 1, 200.0, NOW(), NOW()),
       (2, 2, 1, 200.0, NOW(), NOW());

INSERT INTO holiday (id, name, employee_id, holiday_year_id, approved, created_date, cancelled)
VALUES (1, 'Trip to Amsterdam', 1, 1, TRUE, '2019-02-01', FALSE),
       (2, 'Appointment', 2, 1, NULL, '2019-04-01', FALSE);

INSERT INTO holiday_date (id, date, holiday_period, holiday_id)
VALUES (1, '2019-03-01', 'ALL_DAY', 1),
       (2, '2019-03-02', 'AM', 1),
       (3, '2019-05-29', 'ALL_DAY', 2);

ALTER SEQUENCE hibernate_sequence RESTART WITH 3;
