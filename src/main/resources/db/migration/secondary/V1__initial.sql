CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

CREATE TABLE absence (
    id                 int8    NOT NULL,
    created_by         int8,
    created_date       timestamp,
    last_modified_by   int8,
    last_modified_date timestamp,
    authorized         boolean,
    cancelled          boolean NOT NULL,
    "end"              date,
    reason             text,
    start              date,
    unauthorize_reason varchar(255),
    company_year_id    int8,
    employee_id        int8,
    PRIMARY KEY (id)
);

CREATE TABLE absence_attachments (
    absence_id     int8 NOT NULL,
    attachments_id int8 NOT NULL
);

CREATE TABLE attachment (
    id                 int8 NOT NULL,
    created_by         int8,
    created_date       timestamp,
    last_modified_by   int8,
    last_modified_date timestamp,
    file_name          varchar(255),
    title              varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE company (
    id                                int8    NOT NULL,
    created_by                        int8,
    created_date                      timestamp,
    last_modified_by                  int8,
    last_modified_date                timestamp,
    friday_end                        time,
    friday_start                      time,
    monday_end                        time,
    monday_start                      time,
    saturday_end                      time,
    saturday_start                    time,
    sunday_end                        time,
    sunday_start                      time,
    thursday_end                      time,
    thursday_start                    time,
    tuesday_end                       time,
    tuesday_start                     time,
    wednesday_end                     time,
    wednesday_start                   time,
    default_holiday_entitlement_hours float8  NOT NULL,
    name                              varchar(255),
    totp_required                     boolean NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE company_year (
    id                 int8 NOT NULL,
    created_by         int8,
    created_date       timestamp,
    last_modified_by   int8,
    last_modified_date timestamp,
    name               varchar(255),
    year_end           date,
    year_start         date,
    PRIMARY KEY (id)
);

CREATE TABLE department (
    id                 int8 NOT NULL,
    created_by         int8,
    created_date       timestamp,
    last_modified_by   int8,
    last_modified_date timestamp,
    title              varchar(255),
    parent_id          int8,
    PRIMARY KEY (id)
);

CREATE TABLE employee (
    id                 int8 NOT NULL,
    created_by         int8,
    created_date       timestamp,
    last_modified_by   int8,
    last_modified_date timestamp,
    city               varchar(255),
    country            int4,
    line               varchar(255),
    postal_code        varchar(255),
    province           varchar(255),
    date_of_birth      date,
    friday_end         time,
    friday_start       time,
    monday_end         time,
    monday_start       time,
    saturday_end       time,
    saturday_start     time,
    sunday_end         time,
    sunday_start       time,
    thursday_end       time,
    thursday_start     time,
    tuesday_end        time,
    tuesday_start      time,
    wednesday_end      time,
    wednesday_start    time,
    extension_number   varchar(255),
    mobile_number      varchar(255),
    profile_file_name  varchar(255),
    reference          varchar(255),
    service_end_date   date,
    service_start_date date,
    telephone_number   varchar(255),
    "user"             int8,
    department_id      int8,
    job_role_id        int8,
    manager_id         int8,
    PRIMARY KEY (id)
);

CREATE TABLE holiday (
    id                 int8    NOT NULL,
    created_by         int8,
    created_date       timestamp,
    last_modified_by   int8,
    last_modified_date timestamp,
    approved           boolean,
    cancelled          boolean NOT NULL,
    disapproval_reason varchar(255),
    name               varchar(255),
    company_year_id    int8,
    employee_id        int8,
    PRIMARY KEY (id)
);

CREATE TABLE holiday_date (
    id                 int8 NOT NULL,
    created_by         int8,
    created_date       timestamp,
    last_modified_by   int8,
    last_modified_date timestamp,
    date               date,
    holiday_period     varchar(255),
    holiday_id         int8,
    PRIMARY KEY (id)
);

CREATE TABLE holiday_entitlement (
    id                        int8   NOT NULL,
    created_by                int8,
    created_date              timestamp,
    last_modified_by          int8,
    last_modified_date        timestamp,
    holiday_entitlement_hours float8 NOT NULL,
    company_year_id           int8,
    employee_id               int8,
    PRIMARY KEY (id)
);

CREATE TABLE job_role (
    id                 int8 NOT NULL,
    created_by         int8,
    created_date       timestamp,
    last_modified_by   int8,
    last_modified_date timestamp,
    description        varchar(255),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS absence_attachments
    ADD CONSTRAINT UK_6h9a40y55lhexqb20roqre4ud UNIQUE (attachments_id);

ALTER TABLE IF EXISTS company_year
    ADD CONSTRAINT UK_hq2s8f4e0eqicnmpx7mhh8e26 UNIQUE (name);

ALTER TABLE IF EXISTS holiday_entitlement
    ADD CONSTRAINT UKtrisg726dnckdl7cct1kiw1j0 UNIQUE (employee_id, company_year_id);

ALTER TABLE IF EXISTS absence
    ADD CONSTRAINT FKjfy3l85rl56oknwgd62gnm8oa
        FOREIGN KEY (company_year_id)
            REFERENCES company_year;

ALTER TABLE IF EXISTS absence
    ADD CONSTRAINT FKs69fc883x11wl5lkx9vjhf5ym
        FOREIGN KEY (employee_id)
            REFERENCES employee;

ALTER TABLE IF EXISTS absence_attachments
    ADD CONSTRAINT FKk83ufme7j7mw6x3nw8j3uwsdx
        FOREIGN KEY (attachments_id)
            REFERENCES attachment;

ALTER TABLE IF EXISTS absence_attachments
    ADD CONSTRAINT FKrk4jhq40ke5onwfvi4m4qrhih
        FOREIGN KEY (absence_id)
            REFERENCES absence;

ALTER TABLE IF EXISTS department
    ADD CONSTRAINT FKmgsnnmudxrwqidn4f64q8rp4o
        FOREIGN KEY (parent_id)
            REFERENCES department;

ALTER TABLE IF EXISTS employee
    ADD CONSTRAINT FKbejtwvg9bxus2mffsm3swj3u9
        FOREIGN KEY (department_id)
            REFERENCES department;

ALTER TABLE IF EXISTS employee
    ADD CONSTRAINT FKf07gef1iix45itu9insj3u2n0
        FOREIGN KEY (job_role_id)
            REFERENCES job_role;

ALTER TABLE IF EXISTS employee
    ADD CONSTRAINT FKou6wbxug1d0qf9mabut3xqblo
        FOREIGN KEY (manager_id)
            REFERENCES employee;

ALTER TABLE IF EXISTS holiday
    ADD CONSTRAINT FKoxexevharmkcs6rjy51qply5c
        FOREIGN KEY (company_year_id)
            REFERENCES company_year;

ALTER TABLE IF EXISTS holiday
    ADD CONSTRAINT FKfcn4ebwwpcrk1dbvjqr760hyg
        FOREIGN KEY (employee_id)
            REFERENCES employee;

ALTER TABLE IF EXISTS holiday_date
    ADD CONSTRAINT FK714i2maqoebxfuq8yl1rtr70f
        FOREIGN KEY (holiday_id)
            REFERENCES holiday;

ALTER TABLE IF EXISTS holiday_entitlement
    ADD CONSTRAINT FKj8kquqhglo29ltqnv385oygot
        FOREIGN KEY (company_year_id)
            REFERENCES company_year;

ALTER TABLE IF EXISTS holiday_entitlement
    ADD CONSTRAINT FKa2ktw41epp57wgvgyl9ug850f
        FOREIGN KEY (employee_id)
            REFERENCES employee;
