# Hand on - 4: Difference between JPA, Hibernate and Spring Data JPA

## 1. Java Persistence API (JPA)

**JPA** is a **specification** (JSR 338) — a set of rules/interfaces that define **how** Java objects should be mapped to relational database tables.

### Key Points:
- JPA is **not a library** — it has **no concrete implementation**
- It defines standard annotations: `@Entity`, `@Table`, `@Id`, `@Column`, `@OneToMany`, etc.
- It defines standard interfaces: `EntityManager`, `EntityManagerFactory`, `Query`
- Think of JPA as a **contract / blueprint** that vendors must implement
- Ensures **portability** — you can switch JPA providers without rewriting entity code

```
JPA = Specification (javax.persistence.* package — only interfaces & annotations)
        ↓
Implemented by vendors like:
    • Hibernate (most popular)
    • EclipseLink (reference implementation)
    • OpenJPA (Apache)
```

---

## 2. Hibernate

**Hibernate** is an **ORM (Object Relational Mapping) tool** that provides a **concrete implementation** of the JPA specification.

### Key Points:
- Hibernate **implements JPA** — it provides the actual code behind `EntityManager`
- It also has its own native API (`Session`, `SessionFactory`) that predates JPA
- Translates Java object operations into SQL queries automatically
- Manages caching, lazy loading, connection pooling, schema generation

### Hibernate Native API (without JPA):
```java
// You manage EVERYTHING manually:
Session session = factory.openSession();    // Open connection
Transaction tx = session.beginTransaction(); // Begin transaction
session.save(employee);                     // Execute INSERT
tx.commit();                                // Commit
session.close();                            // Close connection
```

### Hibernate as JPA Provider:
```java
// When used via JPA's EntityManager API:
EntityManager em = emFactory.createEntityManager();
em.getTransaction().begin();
em.persist(employee);
em.getTransaction().commit();
em.close();
```

---

## 3. Spring Data JPA

**Spring Data JPA** is **not another JPA implementation** — it is an **abstraction layer ABOVE** JPA (and Hibernate).

### Key Points:
- Reduces boilerplate code by providing Repository interfaces
- Automatically generates CRUD implementations at runtime (no SQL needed)
- Manages transactions via `@Transactional`
- Uses Hibernate as the underlying JPA provider internally
- Provides derived query methods (method name → SQL)

### Spring Data JPA approach:
```java
// Repository — ZERO implementation code needed!
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> { }

// Service — Clean, concise, readable
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);  // That's it! 1 line.
    }
}
```

---

## 4. Side-by-Side Code Comparison

### ❌ Hibernate (Plain) — ADD Employee

```java
public Integer addEmployee(Employee employee) {
    Session session = factory.openSession();   // boilerplate
    Transaction tx = null;
    Integer employeeID = null;

    try {
        tx = session.beginTransaction();       // boilerplate
        employeeID = (Integer) session.save(employee);  // ← actual logic (1 line)
        tx.commit();                           // boilerplate
    } catch (HibernateException e) {
        if (tx != null) tx.rollback();         // boilerplate
        e.printStackTrace();
    } finally {
        session.close();                       // boilerplate
    }
    return employeeID;
}
// 15 lines — only 1 line is actual business logic!
```

### ✅ Spring Data JPA — ADD Employee

```java
// EmployeeRepository.java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> { }

// EmployeeService.java
@Autowired
private EmployeeRepository employeeRepository;

@Transactional
public void addEmployee(Employee employee) {
    employeeRepository.save(employee);  // ← 1 line, no boilerplate!
}
// 3 lines total (including annotation and method signature)
```

---

## 5. Full CRUD Comparison Table

| Operation    | Hibernate (lines) | Spring Data JPA (lines) |
|--------------|:-----------------:|:-----------------------:|
| INSERT       | 15                | 1 (`save`)              |
| SELECT by ID | 15                | 1 (`findById`)          |
| SELECT ALL   | 15                | 1 (`findAll`)           |
| UPDATE       | 13                | 1 (`save`)              |
| DELETE       | 16                | 1 (`deleteById`)        |
| COUNT        | 10                | 1 (`count`)             |
| **Total**    | **84**            | **6**                   |

> **Spring Data JPA reduces boilerplate code by ~93%!**

---

## 6. Architecture Diagram

```
┌─────────────────────────────────────────────────────────┐
│                   Your Application                       │
│              (OrmLearnApplication.java)                  │
└────────────────────────┬────────────────────────────────┘
                         │ calls
┌────────────────────────▼────────────────────────────────┐
│              Spring Data JPA Layer                       │
│         (EmployeeRepository, EmployeeService)            │
│   • Repository interfaces (JpaRepository)               │
│   • @Transactional management                           │
│   • Derived query method generation                     │
└────────────────────────┬────────────────────────────────┘
                         │ delegates to
┌────────────────────────▼────────────────────────────────┐
│                  JPA Layer (Specification)               │
│                   (javax.persistence.*)                  │
│   • EntityManager, EntityManagerFactory                 │
│   • @Entity, @Table, @Id, @Column annotations           │
│   • JPQL (JPA Query Language)                           │
└────────────────────────┬────────────────────────────────┘
                         │ implemented by
┌────────────────────────▼────────────────────────────────┐
│              Hibernate (JPA Provider / ORM)              │
│   • Session, SessionFactory, Transaction                │
│   • SQL generation from entity mappings                 │
│   • Caching (L1, L2), lazy loading                     │
│   • Schema generation (ddl-auto)                        │
└────────────────────────┬────────────────────────────────┘
                         │ connects to
┌────────────────────────▼────────────────────────────────┐
│              MySQL Database (ormlearn schema)            │
│   • country table, employee table etc.                  │
└─────────────────────────────────────────────────────────┘
```

---

## 7. Summary Table

| Feature                      | JPA              | Hibernate              | Spring Data JPA           |
|------------------------------|------------------|------------------------|---------------------------|
| Type                         | Specification    | ORM Implementation     | Abstraction Layer          |
| Package                      | `javax.persistence` | `org.hibernate`     | `org.springframework.data` |
| Has concrete implementation? | ❌ No             | ✅ Yes                 | ❌ No (uses Hibernate)     |
| Manages transactions?        | Manual           | Manual                 | ✅ Auto (@Transactional)   |
| CRUD boilerplate?            | Medium           | High                   | ✅ Zero                    |
| Custom queries?              | JPQL             | HQL / Criteria         | Method names / @Query      |
| Learning curve               | Medium           | High                   | Low                        |
| Portability                  | High             | Medium (Hibernate lock) | High                      |

---

## 8. Key Takeaways

1. **JPA** = The *what* (rules & interfaces, no code)
2. **Hibernate** = The *how* (implements JPA + adds its own Session API)
3. **Spring Data JPA** = The *easy button* (wraps JPA/Hibernate, eliminates boilerplate)

> In a Spring Boot project: **Your code → Spring Data JPA → JPA → Hibernate → MySQL**

---

## Reference Links

- [DZone: Difference between Hibernate and Spring Data JPA](https://dzone.com/articles/what-is-the-difference-between-hibernate-and-sprin-1)
- [JavaWorld: Introduction to JPA](https://www.javaworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html)
