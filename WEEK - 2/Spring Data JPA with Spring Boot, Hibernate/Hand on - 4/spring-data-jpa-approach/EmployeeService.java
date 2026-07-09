package com.cognizant.ormlearn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.repository.EmployeeRepository;

/**
 * ============================================================
 * SPRING DATA JPA APPROACH — Service Class
 * ============================================================
 *
 * Compare this to the Hibernate EmployeeDAO.java:
 *
 *   Hibernate EmployeeDAO   → 100+ lines, 4 methods, all boilerplate
 *   Spring Data JPA Service → Clean, concise business logic only
 *
 * Layers in Spring Data JPA:
 *   Controller/Main → Service (@Service) → Repository (@Repository) → Database
 *
 * ============================================================
 */
@Service
public class EmployeeService {

    /**
     * @Autowired:
     *   Spring injects the auto-generated EmployeeRepository
     *   implementation (proxy) into this field at startup.
     *   No need to instantiate or configure anything manually.
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    // ----------------------------------------------------------
    // CREATE: Add a new Employee
    // ----------------------------------------------------------

    /**
     * Hibernate equivalent: 15 lines (openSession, beginTx, save, commit, rollback, close)
     * Spring Data JPA:       2 lines  ← This is the entire method!
     *
     * @Transactional ensures:
     *   - A DB transaction is opened before the method runs.
     *   - Committed automatically if method completes normally.
     *   - Rolled back automatically if an exception is thrown.
     *   - No try/catch/finally needed!
     */
    @Transactional
    public void addEmployee(Employee employee) {
        // save() performs INSERT if employee.id is null
        // save() performs UPDATE if employee.id already exists
        employeeRepository.save(employee);
    }

    // ----------------------------------------------------------
    // READ: Get an Employee by ID
    // ----------------------------------------------------------

    /**
     * Hibernate equivalent: 15 lines of boilerplate
     * Spring Data JPA:       3 lines
     *
     * findById() returns Optional<Employee> — a modern Java 8 way to
     * handle the case where the record might not exist (avoids NullPointerException).
     */
    @Transactional
    public Employee getEmployee(Integer id) {
        // Optional<T> safely handles "not found" case
        Optional<Employee> result = employeeRepository.findById(id);
        // orElse(null) returns null if not found (or throw a custom exception)
        return result.orElse(null);
    }

    // ----------------------------------------------------------
    // READ ALL: Get all Employees
    // ----------------------------------------------------------

    /**
     * Hibernate equivalent: 15+ lines
     * Spring Data JPA:       1 line!
     *
     * findAll() executes: SELECT * FROM employee
     */
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ----------------------------------------------------------
    // UPDATE: Modify an Employee
    // ----------------------------------------------------------

    /**
     * Hibernate equivalent: 13 lines of boilerplate
     * Spring Data JPA:       2 lines
     *
     * save() is smart — it uses employee.id to decide INSERT or UPDATE.
     * If id exists in DB → UPDATE; if id is null or new → INSERT.
     */
    @Transactional
    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);   // same method as addEmployee!
    }

    // ----------------------------------------------------------
    // DELETE: Remove an Employee by ID
    // ----------------------------------------------------------

    /**
     * Hibernate equivalent: 16 lines of boilerplate
     * Spring Data JPA:       1 line!
     *
     * deleteById() executes: DELETE FROM employee WHERE emp_id = ?
     */
    @Transactional
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    // ----------------------------------------------------------
    // COUNT: Total number of Employees
    // ----------------------------------------------------------

    /**
     * Hibernate equivalent: 10+ lines of HQL query boilerplate
     * Spring Data JPA:       1 line!
     *
     * count() executes: SELECT COUNT(*) FROM employee
     */
    @Transactional
    public long getEmployeeCount() {
        return employeeRepository.count();
    }
}

/*
 * ============================================================
 * LINES OF CODE COMPARISON
 * ============================================================
 *
 *  OPERATION     HIBERNATE (DAO)    SPRING DATA JPA (Service)
 *  ---------     ---------------    -------------------------
 *  addEmployee   15 lines           1 line  (save)
 *  getEmployee   15 lines           1 line  (findById)
 *  getAllEmp      15 lines           1 line  (findAll)
 *  updateEmp     13 lines           1 line  (save)
 *  deleteEmp     16 lines           1 line  (deleteById)
 *  countEmp      10 lines           1 line  (count)
 *  ---------     ---------------    -------------------------
 *  TOTAL         84 lines           6 lines
 *
 *  Spring Data JPA reduces boilerplate by ~93%!
 * ============================================================
 */
