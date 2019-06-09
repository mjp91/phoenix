INSERT INTO user (id, username, full_name)
VALUES (1, 'buzz', 'Buzz Lightyear'),
       (2, 'matt', 'Matthew Pearsall');

INSERT INTO employee (id, user_id, average_day_length, manager_id)
VALUES (1, 1, 7.5, NULL),
       (2, 2, 7.5, 1);

INSERT INTO holiday_year (id, name, year_start, year_end)
VALUES (1, '2019', '2019-01-01', '2019-12-31');

INSERT INTO holiday_entitlement (id, employee_id, holiday_year_id, holiday_entitlement_hours)
VALUES (1, 1, 1, 187.5),
       (2, 2, 1, 187.5);

INSERT INTO holiday (id, name, employee_id, holiday_year_id, approved, created_date)
VALUES (1, 'Trip to Amsterdam', 1, 1, TRUE, '2019-02-01'),
       (2, 'Appointment', 2, 1, NULL, '2019-04-01');

INSERT INTO holiday_date (id, date, holiday_period)
VALUES (1, '2019-03-01', 'ALL_DAY'),
       (2, '2019-03-02', 'AM'),
       (3, '2019-05-29', 'ALL_DAY');

INSERT INTO holiday_holiday_dates
VALUES (1, 1),
       (1, 2),
       (2, 3);