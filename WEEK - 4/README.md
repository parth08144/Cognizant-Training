# Week 4 – Mandatory Hands-on Answers (with full project file structure)
**Topic (per your tracker image):** Code quality and Sonarqube · Microservices with Spring Boot 3 and Spring Cloud

## Important note on scope

I checked `DN_Java_FSE_Mandatory_handson_detail.xlsx` closely for Week 4. It contains **no row at all for "Code quality and Sonarqube"** — no mandatory exercise, and no "additional important" one either. SonarQube is a static-analysis tool you run against a project rather than something with its own hands-on code, so this is likely intentional (there's no code exercise to hand you for it). If your actual course materials do have a Sonar-specific hands-on file that just isn't reflected in this tracker spreadsheet, send it over and I'll do it properly.

What **is** in the tracker for Week 4:

| Skill | Source file | Mandatory Exercise | Additional (not mandatory) |
|---|---|---|---|
| Microservices with Spring Boot 3 and Spring Cloud | 2. Microservices with API gateway | **Creating Microservices for account and loan** | Create Eureka Discovery Server and register microservices; API Gateway with global logging filter |

---

## Complete file structure

```
Week4_Handson/
├── README.md
└── microservices/
    ├── account/                         <- MANDATORY
    │   ├── pom.xml
    │   └── src/main/java/com/cognizant/account/
    │       ├── AccountApplication.java
    │       └── controller/AccountController.java
    │   └── src/main/resources/application.properties   (port 8080)
    │
    ├── loan/                            <- MANDATORY
    │   ├── pom.xml
    │   └── src/main/java/com/cognizant/loan/
    │       ├── LoanApplication.java
    │       └── controller/LoanController.java
    │   └── src/main/resources/application.properties   (port 8081)
    │
    ├── eureka-discovery-server/         <- additional
    │   ├── pom.xml
    │   └── src/main/java/com/cognizant/eureka/EurekaDiscoveryServerApplication.java
    │   └── src/main/resources/application.properties   (port 8761)
    │
    ├── greet-service/                   <- additional (supports the API Gateway demo)
    │   ├── pom.xml
    │   └── src/main/java/com/cognizant/greet/
    │       ├── GreetServiceApplication.java
    │       └── controller/GreetController.java
    │   └── src/main/resources/application.properties   (port 8082)
    │
    └── api-gateway/                     <- additional
        ├── pom.xml
        └── src/main/java/com/cognizant/gateway/
            ├── ApiGatewayApplication.java
            └── filter/LogFilter.java
        └── src/main/resources/application.properties   (port 9090)
```

Each folder is an **independent, standalone Maven project** — exactly as the hands-on describes ("Each microservice will be a specific independent Spring RESTful Webservice maven project having its own pom.xml"). Import each one separately into Eclipse/IntelliJ as "Existing Maven Project", or `cd` into each and run `mvn spring-boot:run`.

*(Built on Spring Boot 3.2.5 + Spring Cloud 2023.0.1 "Leyton" — the current compatible release train, since your course targets Spring Boot 3.)*

---

## 1. MANDATORY: Creating Microservices for account and loan

### Account Microservice (`microservices/account`)
```
GET /accounts/{number}
```
Dummy response, no backend connectivity, exactly as specified:
```bash
cd microservices/account
mvn spring-boot:run
# runs on http://localhost:8080

curl http://localhost:8080/accounts/00987987973432
# {"number":"00987987973432","type":"savings","balance":234343}
```

### Loan Microservice (`microservices/loan`)
```
GET /loans/{number}
```
```bash
cd microservices/loan
mvn spring-boot:run
# runs on http://localhost:8081 (8080 is already taken by account-service)

curl http://localhost:8081/loans/H00987987972342
# {"number":"H00987987972342","type":"car","loan":400000,"emi":3258,"tenure":18}
```

**Key concept to remember (from the hands-on):** each service is a fully separate Maven project with its own `pom.xml` — nothing is shared except the general folder they live under. If both try to start on the default port 8080 simultaneously you'll get "port already in use", which is exactly why `loan` is configured with `server.port=8081`.

---

## 2. Additional (not mandatory): Eureka Discovery Server + registration

```bash
# 1) Start the discovery server first
cd microservices/eureka-discovery-server
mvn spring-boot:run
# open http://localhost:8761 -> "Instances currently registered with Eureka" is empty

# 2) Start account and loan (already have @EnableDiscoveryClient + eureka.client config)
cd microservices/account && mvn spring-boot:run
cd microservices/loan && mvn spring-boot:run

# 3) Refresh http://localhost:8761 -> ACCOUNT-SERVICE and LOAN-SERVICE now appear
```

---

## 3. Additional (not mandatory): API Gateway with global logging filter

Demonstrated with the `greet-service` microservice (per the hands-on's own walkthrough), routed through the gateway:

```bash
# Start in this order: eureka-discovery-server -> greet-service -> api-gateway
cd microservices/eureka-discovery-server && mvn spring-boot:run
cd microservices/greet-service          && mvn spring-boot:run
cd microservices/api-gateway            && mvn spring-boot:run

curl http://localhost:9090/greet-service/greet
# Hello World
```

`LogFilter.java` is a `GlobalFilter` (`spring-cloud-starter-gateway`'s modern equivalent of the hands-on's filter) that logs the method + URI of every request the gateway receives, before it forwards to the target microservice — check the `api-gateway` console output after calling the URL above.

---

*Let me know if you want the SonarQube setup steps written up separately (project setup + `mvn sonar:sonar` / SonarQube Scanner configuration) even though it isn't in the mandatory tracker — happy to add it as a bonus section. Otherwise, say the word for Week 5 (Angular/React).*
