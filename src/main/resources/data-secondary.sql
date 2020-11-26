INSERT INTO company (id, name, default_holiday_entitlement_hours, monday_start, monday_end, created_date,
                     last_modified_date, totp_required)
VALUES (1, 'Holibyte', '200.0', '09:00', '17:00', NOW(), NOW(), TRUE);

INSERT INTO job_role (id, description, created_date, last_modified_date)
VALUES (1, 'HR Assistant', NOW(), NOW()),
       (2, 'Software Development Manager', NOW(), NOW()),
       (3, 'Software Developer', NOW(), NOW());

INSERT INTO department (id, title, parent_id, created_date, last_modified_date)
VALUES (1, 'Central Services', NULL, NOW(), NOW()),
       (2, 'Human Resources', 1, NOW(), NOW()),
       (3, 'Software', NULL, NOW(), NOW()),
       (4, 'Software Development', 3, NOW(), NOW());

INSERT INTO employee (id, "user", manager_id, monday_start, monday_end, created_date, last_modified_date, job_role_id,
                      department_id, date_of_birth, service_start_date)
VALUES (1, 1, NULL, '09:00', '17:00', NOW(), NOW(), 2, 3, '1976-09-05', '2018-03-04'),
       (2, 2, 1, '09:00', '17:00', NOW(), NOW(), 3, 4, '1991-12-01', '2015-11-23'),
       (3, 3, 1, '09:00', '17:00', NOW(), NOW(), 3, 4, '1987-04-20', '2001-02-21');

INSERT INTO company_year (id, name, year_start, year_end, created_date, last_modified_date)
VALUES (1, '2019', '2019-01-01', '2019-12-31', NOW(), NOW());

INSERT INTO holiday_entitlement (id, employee_id, company_year_id, holiday_entitlement_hours, created_date,
                                 last_modified_date)
VALUES (1, 1, 1, 200.0, NOW(), NOW()),
       (2, 2, 1, 200.0, NOW(), NOW()),
       (3, 3, 1, 200.0, NOW(), NOW());

INSERT INTO holiday (id, name, employee_id, company_year_id, approved, created_date, cancelled)
VALUES (1, 'Trip to Amsterdam', 1, 1, TRUE, '2019-02-01', FALSE),
       (2, 'Appointment', 2, 1, NULL, '2019-04-01', FALSE);

INSERT INTO holiday_date (id, date, holiday_period, holiday_id)
VALUES (1, '2019-03-01', 'ALL_DAY', 1),
       (2, '2019-03-02', 'AM', 1),
       (3, '2019-05-29', 'ALL_DAY', 2);

INSERT INTO absence (id, employee_id, company_year_id, start, "end", reason, authorized, cancelled)
VALUES (1, 1, 1, '2019-10-01', '2019-10-02', 'Flu', NULL, FALSE),
       (2, 2, 1, '2019-11-04', '2019-11-08', 'Cold', NULL, FALSE);

ALTER SEQUENCE hibernate_sequence RESTART WITH 5;
