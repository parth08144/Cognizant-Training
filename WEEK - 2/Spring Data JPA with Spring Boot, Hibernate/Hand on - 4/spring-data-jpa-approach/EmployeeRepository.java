package com.cognizant.ormlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Employee;

/**
 * ============================================================
 * SPRING DATA JPA APPROACH — Repository Interface
 * ============================================================
 *
 * This is the COMPLETE data-access layer for Employee!
 * Compare this to EmployeeDAO.java (Hibernate approach) which
 * had 100+ lines of boilerplate code.
 *
 * How does this work with ZERO implementation code?
 *   Spring Data JPA uses Java Dynamic Proxies at runtime to
 *   auto-generate an implementation class for this interface.
 *   Hibernate acts as the underlying JPA provider that actually
 *   executes the SQL queries.
 *
 * JpaRepository<Employee, Integer> provides out-of-the-box:
 *
 *   METHOD                     SQL GENERATED
 *   ------                     -------------
 *   save(employee)          -> INSERT INTO employee (...)
 *                              or UPDATE employee SET ... WHERE emp_id=?
 *   findById(id)            -> SELECT * FROM employee WHERE emp_id=?
 *   findAll()               -> SELECT * FROM employee
 *   deleteById(id)          -> DELETE FROM employee WHERE emp_id=?
 *   count()                 -> SELECT COUNT(*) FROM employee
 *   existsById(id)          -> SELECT 1 FROM employee WHERE emp_id=?
 *   saveAll(list)           -> Batch INSERT/UPDATE
 *
 * Type parameters:
 *   Employee -> the entity type this repository manages
 *   Integer  -> the type of Employee's @Id (primary key)
 *
 * @Repository:
 *   - Marks as Spring Data Repository bean
 *   - Exception translation: converts SQL exceptions to Spring's
 *     DataAccessException (no Hibernate-specific exceptions leak out)
 * ============================================================
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // ✅ No code needed! All CRUD operations are inherited from JpaRepository.
    //
    // You CAN add custom query methods using method naming conventions:
    //
    //   findByFirstName(String name)
    //       -> SELECT * FROM employee WHERE emp_first_name = ?
    //
    //   findBySalaryGreaterThan(double amount)
    //       -> SELECT * FROM employee WHERE emp_salary > ?
    //
    //   findByFirstNameAndLastName(String fn, String ln)
    //       -> SELECT * FROM employee WHERE emp_first_name=? AND emp_last_name=?
    //
    // Spring Data JPA parses the method name and generates the SQL automatically!

}
