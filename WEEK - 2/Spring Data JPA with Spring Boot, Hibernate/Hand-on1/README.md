# Hand-on 1: Spring Data JPA - Quick Example

## Overview

This project demonstrates **Spring Data JPA** with **Hibernate ORM** to connect a **Spring Boot** application to a **MySQL** database and perform database read operations.

---

## Project Structure

```
Hand-on1/
├── pom.xml                                         ← Maven build & dependency config
├── database-setup.sql                              ← MySQL DDL & DML script
└── src/
    ├── main/
    │   ├── java/com/cognizant/ormlearn/
    │   │   ├── OrmLearnApplication.java            ← Main class (@SpringBootApplication)
    │   │   ├── model/
    │   │   │   └── Country.java                    ← JPA Entity (@Entity, @Table)
    │   │   ├── repository/
    │   │   │   └── CountryRepository.java          ← Spring Data Repository (@Repository)
    │   │   └── service/
    │   │       └── CountryService.java             ← Service Layer (@Service)
    │   └── resources/
    │       └── application.properties              ← DB config, Hibernate, Logging
    └── test/
        └── java/com/cognizant/ormlearn/
            └── OrmLearnApplicationTests.java       ← Context load smoke test
```

---

## Technology Stack

| Technology         | Version   | Purpose                                       |
|--------------------|-----------|-----------------------------------------------|
| Spring Boot        | 2.7.18    | Application framework and auto-configuration  |
| Spring Data JPA    | (managed) | Repository abstraction over Hibernate         |
| Hibernate ORM      | (managed) | JPA provider — translates Java ↔ SQL          |
| MySQL Connector/J  | (managed) | JDBC driver for MySQL 8.x                     |
| Spring DevTools    | (managed) | Auto-restart during development               |
| SLF4J + Logback    | (managed) | Logging framework                             |

---

## Step-by-Step Setup

### Step 1: MySQL Database Setup

Open **MySQL Workbench** or run `mysql -u root -p` in terminal and execute:

```sql
CREATE SCHEMA IF NOT EXISTS ormlearn;
USE ormlearn;

CREATE TABLE country (
    co_code VARCHAR(2)  NOT NULL PRIMARY KEY,
    co_name VARCHAR(50) NOT NULL
);

INSERT INTO country VALUES ('IN', 'India');
INSERT INTO country VALUES ('US', 'United States of America');

SELECT * FROM country;
```

> Or simply run the provided **`database-setup.sql`** script.

---

### Step 2: Configure Database Credentials

Open `src/main/resources/application.properties` and update if needed:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ormlearn
spring.datasource.username=root
spring.datasource.password=root   ← Change to your MySQL root password
```

---

### Step 3: Build the Project (Maven)

```bash
mvn clean package
```

> **If behind a Cognizant proxy**, use:
> ```bash
> mvn clean package -Dhttp.proxyHost=proxy.cognizant.com -Dhttp.proxyPort=6050 -Dhttps.proxyHost=proxy.cognizant.com -Dhttps.proxyPort=6050
> ```

---

### Step 4: Run the Application

**From Eclipse:**
- Right-click `OrmLearnApplication.java` → **Run As → Java Application**

**From Command Line:**
```bash
mvn spring-boot:run
```

---

## Expected Console Output

```
18-07-26 10:00:00.001  main  INFO  OrmLearnApplication  main  52  Inside main
18-07-26 10:00:00.002  main  INFO  CountryService       testGetAllCountries  30  Start
18-07-26 10:00:00.003  main TRACE  SQL                  n/a   n/a  select country0_.co_code as co_code1_0_, country0_.co_name as co_name2_0_ from country country0_
18-07-26 10:00:00.010  main DEBUG  OrmLearnApplication  testGetAllCountries  61  countries=[Country{code='IN', name='India'}, Country{code='US', name='United States of America'}]
18-07-26 10:00:00.011  main  INFO  OrmLearnApplication  testGetAllCountries  62  End
```

---

## Key Concepts Explained

### @SpringBootApplication
Combines 3 annotations:
1. **@Configuration** — This class defines Spring beans
2. **@EnableAutoConfiguration** — Auto-configures DataSource, JPA, Hibernate based on classpath
3. **@ComponentScan** — Scans `com.cognizant.ormlearn` package for beans

### @Entity & @Table
- `@Entity` — Marks `Country` class as a JPA-managed persistent object
- `@Table(name="country")` — Maps to the `country` database table

### @Repository & JpaRepository
- `@Repository` — Marks interface as a Spring Data repository
- `JpaRepository<Country, String>` — Provides built-in CRUD methods (findAll, save, delete, etc.)
- Spring auto-generates the implementation at runtime — **no SQL needed!**

### @Service & @Transactional
- `@Service` — Marks the class as business logic layer
- `@Transactional` — Wraps method in a DB transaction; auto-rollback on failure

### ApplicationContext
- The Spring IoC (Inversion of Control) container
- Manages all Spring beans and their lifecycle
- `context.getBean(CountryService.class)` retrieves a managed bean

---

## Layer Architecture

```
OrmLearnApplication (main)
        │
        ▼
  CountryService          ← @Service  (Business Logic Layer)
        │
        ▼
CountryRepository         ← @Repository (Data Access Layer)
        │
        ▼
   Hibernate ORM          ← JPA Provider (translates Java ↔ SQL)
        │
        ▼
   MySQL Database         ← ormlearn.country table
```

---

## Troubleshooting

| Error | Cause | Fix |
|-------|-------|-----|
| `Access denied for user 'root'` | Wrong password | Update `spring.datasource.password` in `application.properties` |
| `Unknown database 'ormlearn'` | Schema not created | Run `database-setup.sql` in MySQL |
| `Table 'country' doesn't exist` | Table not created | Run `CREATE TABLE` from `database-setup.sql` |
| `Schema-validation: missing column` | Column name mismatch | Check `@Column(name=...)` in `Country.java` matches actual column names |
| `Could not create connection to database` | MySQL not running | Start MySQL service |
