This is a project for Sourcery admission.

The projects uses Springboot, hibernate, lombok, PostgreSQL for running the application and an in-memory H2 database for tests.

1. To start using the applicaiton first what is needed is to start the dockerized PostgreSQL database:

```bash
docker-compose up
```

2. Then add a datasource or use any other database viewer. The database information can be found in the application.properties file:

```properties
spring.application.name=Sourcery

spring.datasource.url=jdbc:postgresql://localhost:5432/db
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```
