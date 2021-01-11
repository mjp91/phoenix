# Phoenix HRMS

Web based Human Resource Management System

![build](https://github.com/mjp91/phoenix/workflows/Java%20CI%20with%20Maven/badge.svg)
![Node.js CI](https://github.com/mjp91/phoenix/workflows/Node.js%20CI/badge.svg)

## Table of Contents

1. [Overview](#overview)
2. [Screenshots](#screenshots)
3. [Features](#features)
4. [Installation](#installation)
5. [Contribution](#contributing)

## Overview

Phoenix is a web application to support common human resource requirements.  
The application tier uses Spring Boot including Spring Web MVC, Security and Data JPA.  
The front-end uses VueJS.

## Screenshots

### Dashboard

![](docs/images/dashboard.png)

### User Lookup

![](docs/images/lookup.png)

### Employee Record

![](docs/images/user.png)

## Features

The following is a list of the main features.

### Employee Database

Record employee details, including a photograph, job role, manager, department, address and holiday entitlement.  
Quick search displaying contact details including email address and telephone.

### Holiday Booking

Employees can request holiday up to their entitlement, requests can be approved or declined by managers.

### Calendar Feeds

Each user has their own personal iCal URL to import holiday into mail clients such as Outlook.

### Absence Reporting

Employees can report absence, absences can be authorized/unauthorized by managers.

### LDAP and Two-Factor Authentication

Optional LDAP login is supported as well as 2FA using TOTP codes.

### Multi-tenancy

Support for multiple clients served by one instance of the application.

## Installation

The easiest way to deploy the application is with Docker Compose.  
There is a sample `docker-compose.yml` file included in the project which is configured to build and deploy the
application.

```shell
git clone https://github.com/mjp91/phoenix.git
cd phoenix

docker-compose up -d
```

### Production

The default passwords included in the `docker-compose.yml` file should be changed.  
The NGINX proxy container should be configured to use TLS.

### Configuration

Configuration properties are provided to the application through environment variables.

Name | Description
--- | ---
`SPRING_DATASOURCE_URL` | JDBC URL of primary database
`SPRING_DATASOURCE_DATA_USERNAME` | Database username of primary database
`SPRING_DATASOURCE_DATA_PASSWORD` | Database password of primary database
`HR_DATASOURCE_URL` | JDBC URL of secondary database
`HR_DATASOURCE_USERNAME` | Database username of secondary database
`HR_DATASOURCE_PASSWORD` | Database password of secondary database
`HR_ADMIN_PASSWORD` | Password of administrator user created on first run
`HR_JWT_SECRET` | Secret used to sign JWTs
`SPRING_MAIL_HOST` | (Optional) SMTP host for email
`SPRING_MAIL_PORT` | (Optional) SMTP port
`SPRING_MAIL_USERNAME` | (Optional) SMTP username
`SPRING_MAIL_PASSWORD` | (Optional) SMTP password
`SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH` | (Optional) SMTP authentication
`SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE` | (Optional) SMTP TLS

## Contributing

Contribution is welcome.

When contributing to this repository, please first discuss the change you wish to make via issue, email, or any other
method with the owners of this repository before making a change.

### Build

#### Required Tools

- JDK 14
- NodeJS
- NPM/Yarn

#### Back-end

From the root of the project; using a local installation of Maven or the included wrapper:

```shell
# compile
./mvnw clean package -DskipTests

# run
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Back-end becomes available from http://localhost:8080.

#### Front-end

From the `ui` directory:

```shell
npm install

npm run serve
```

Front-end becomes available from http://localhost:8081.
