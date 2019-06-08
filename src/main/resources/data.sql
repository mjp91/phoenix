INSERT INTO user (id, username, full_name)
VALUES (1, 'buzz', 'Buzz Lightyear');

INSERT INTO employee (id, user_id, average_day_length)
VALUES (1, 1, 7.5);

INSERT INTO holiday_year (id, year_start, year_end)
VALUES (1, '2019-01-01', '2019-12-31');

INSERT INTO holiday_entitlement (id, employee_id, holiday_year_id, holiday_entitlement_hours)
VALUES (1, 1, 1, 187.5);

INSERT INTO holiday (id, employee_id, holiday_year_id, approved)
VALUES (1, 1, 1, TRUE);

INSERT INTO holiday_date (id, date, holiday_period)
VALUES (1, '2019-03-01', 'ALL_DAY'),
       (2, '2019-03-02', 'AM');

INSERT INTO holiday_holiday_dates
VALUES (1, 1),
       (1, 2);