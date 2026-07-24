# Week 3 – Mandatory Hands-on Answers (with full project file structure)
**Topic:** Spring REST using Spring Boot 3

Per `DN_Java_FSE_Mandatory_handson_detail.xlsx`, the **mandatory** exercises for Week 3 are:

| Source file | Mandatory Exercises |
|---|---|
| 1. spring-rest-handson.docx | Create a Spring Web Project using Maven · Spring Core – Load Country from Spring Configuration XML |
| 2. spring-rest-handson.docx | Hello World RESTful Web Service · REST - Country Web Service · REST - Get country based on country code |
| 5. JWT-handson.docx | Create authentication service that returns JWT |

*(3. spring-rest-handson.docx is listed only as "Additional important" — POST/PUT/DELETE + validation isn't in the tracker at all, so it's skipped here.)*

All of the above live together in **one Spring Boot 3 Maven project** called `spring-learn`, exactly as the hands-on docs build it up incrementally.

```
Week3_Handson/
├── README.md
└── spring-learn/
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/com/cognizant/springlearn/
        │   │   ├── SpringLearnApplication.java         <- Hands on 1: Maven/Spring Boot project setup
        │   │   ├── Country.java                        <- Hands on 4: Country bean (POJO)
        │   │   ├── controller/
        │   │   │   ├── HelloController.java            <- Hello World RESTful Web Service
        │   │   │   ├── CountryController.java          <- Country Web Service + Get by code
        │   │   │   └── AuthenticationController.java   <- JWT-handson: /authenticate
        │   │   ├── service/
        │   │   │   ├── CountryService.java              <- loads country.xml, get/find logic
        │   │   │   └── exception/CountryNotFoundException.java
        │   │   └── security/
        │   │       ├── SecurityConfig.java              <- users/roles + filter chain
        │   │       ├── JwtUtil.java                     <- generate/parse JWT
        │   │       └── JwtAuthorizationFilter.java       <- validates Bearer token per request
        │   └── resources/
        │       ├── application.properties
        │       └── country.xml                          <- Hands on 4: Spring XML bean config
        └── test/
            └── java/com/cognizant/springlearn/
                └── SpringLearnApplicationTests.java      <- MockMvc tests
```

---

## How to run

```bash
cd spring-learn
mvn clean package
mvn spring-boot:run
```
The app starts on **port 8083** (as specified in the hands-on docs).

---

## 1. Hands on 1: Create a Spring Web Project using Maven (mandatory)

- `pom.xml` — created the same way `start.spring.io` would (group `com.cognizant`, artifact `spring-learn`), with `spring-boot-starter-web` and `spring-boot-devtools`.
- `SpringLearnApplication.java` — the `@SpringBootApplication` main class, with a start-up log line to verify `main()` ran (`Inside main`).

---

## 2. Hands on 4: Spring Core – Load Country from Spring Configuration XML (mandatory)

- `country.xml` — defines 4 `<bean>` entries (`in`, `us`, `de`, `jp`) each mapping to a `Country` POJO, plus a `countryList` `ArrayList` bean referencing all four (`<ref bean="..."/>`).
- `Country.java` — plain class with `code`/`name`, a debug log in the no-arg constructor ("Inside Country Constructor."), and debug logs in every getter/setter, per the hands-on instructions.
- `CountryService`'s constructor loads this file with `new ClassPathXmlApplicationContext("country.xml")` — the classic Spring Core technique — and feeds the REST layer below with the resulting beans.

---

## 3. Hello World RESTful Web Service (mandatory)

`HelloController.java`:
```
GET /hello  ->  "Hello World!!"
```
```bash
curl http://localhost:8083/hello
```

---

## 4. REST - Country Web Service (mandatory)

`CountryController.getCountryIndia()` loads the `in` bean straight from `country.xml` (via `CountryService.getCountryIndia()`) and returns it as JSON.

```
GET /country
```
```bash
curl http://localhost:8083/country
# {"code":"IN","name":"India"}
```

---

## 5. REST - Get country based on country code (mandatory)

`CountryController.getCountry(code)` → `CountryService.getCountry(code)` iterates the `countryList` loaded from `country.xml` and does a **case-insensitive** match (`equalsIgnoreCase`). If nothing matches, `CountryNotFoundException` (annotated `@ResponseStatus(HttpStatus.NOT_FOUND, reason="Country not found")`) turns into a 404 automatically.

```
GET /countries/{code}
```
```bash
curl http://localhost:8083/countries/in     # 200 {"code":"IN","name":"India"}
curl http://localhost:8083/countries/IN     # same result — case-insensitive
curl -i http://localhost:8083/countries/zz  # 404 Country not found
```

(`GET /countries` returning the full list is also included as supporting infrastructure the above endpoint relies on — it isn't separately mandatory, but comes for free.)

---

## 6. JWT-handson: Create authentication service that returns JWT (mandatory)

Because this project targets **Spring Boot 3 / Spring Security 6**, `WebSecurityConfigurerAdapter` (used in the older hands-on doc) is deprecated/removed — `SecurityConfig.java` uses the modern `SecurityFilterChain` bean instead, but implements exactly the same behavior described in the doc:

- Two in-memory users: `admin/pwd` (`ROLE_ADMIN`) and `user/pwd` (`ROLE_USER`).
- `/countries`, `/countries/**`, `/country` require `ROLE_USER`.
- `/authenticate` requires either role (HTTP Basic).
- `/hello` is open.
- A custom `JwtAuthorizationFilter` runs before Spring Security's normal login filter, so a valid `Authorization: Bearer <token>` header authenticates the request without needing Basic Auth again.

`AuthenticationController.authenticate()` reads the `Authorization: Basic ...` header Spring Security already validated, decodes the username, and calls `JwtUtil.generateJwt(user)` (uses `io.jsonwebtoken` / JJWT, HS256, configurable secret + expiry in `application.properties`).

```
GET /authenticate
```
```bash
# Step 1: authenticate with Basic auth to obtain a token
curl -s -u user:pwd http://localhost:8083/authenticate
# {"token":"eyJhbGciOiJIUzI1NiJ9....."}

# Step 2: use the token as a Bearer credential on a protected endpoint
curl -s -H "Authorization: Bearer <TOKEN_FROM_STEP_1>" http://localhost:8083/countries

# Step 3: tampering with the token, or omitting it while also omitting Basic auth, is rejected
curl -i http://localhost:8083/countries
# 401 Unauthorized
```

---

## Testing

`SpringLearnApplicationTests.java` uses `MockMvc` + `@WithMockUser(roles = "USER")` to verify:
- the Spring context loads and `CountryController` is wired,
- `GET /country` returns India,
- `GET /countries/{code}` is case-insensitive,
- an unknown code returns 404.

```bash
mvn test
```

---

*Say the word when you'd like Week 4 (Code Quality/SonarQube and Microservices with Spring Boot 3 & Spring Cloud) in the same format.*
