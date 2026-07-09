package com.cognizant.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * ============================================================
 * HIBERNATE APPROACH (Without Spring Data JPA)
 * ============================================================
 *
 * This class demonstrates how you interact with the database
 * using PLAIN HIBERNATE — without Spring Data JPA.
 *
 * Key observations:
 *  1. You must manually open a Session.
 *  2. You must manually begin a Transaction.
 *  3. You must manually commit the transaction.
 *  4. You must manually rollback on exception.
 *  5. You must manually close the Session in finally block.
 *  6. Every CRUD method repeats this boilerplate code!
 *
 * This is verbose, error-prone and hard to maintain.
 * Spring Data JPA eliminates ALL of this boilerplate.
 * ============================================================
 */
public class EmployeeDAO {

    // SessionFactory is a heavyweight object — created once per application.
    // It reads hibernate.cfg.xml and builds the connection pool.
    private static SessionFactory factory;

    static {
        try {
            // Reads hibernate.cfg.xml for DB settings and entity mappings
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // ----------------------------------------------------------
    // CREATE: Add a new Employee record
    // ----------------------------------------------------------

    /**
     * Hibernate CREATE - 15+ lines of boilerplate for a simple INSERT!
     *
     * Spring Data JPA equivalent:
     *     employeeRepository.save(employee);   ← Just 1 line!
     */
    public Integer addEmployee(Employee employee) {
        Session session = factory.openSession();   // Step 1: Open session (DB connection)
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();       // Step 2: Begin transaction manually
            employeeID = (Integer) session.save(employee);  // Step 3: Save entity
            tx.commit();                           // Step 4: Commit manually
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();         // Step 5: Rollback on error manually
            e.printStackTrace();
        } finally {
            session.close();                       // Step 6: Always close session manually
        }
        return employeeID;
    }

    // ----------------------------------------------------------
    // READ: Get an Employee by ID
    // ----------------------------------------------------------

    /**
     * Hibernate READ - Again, same boilerplate repeated!
     *
     * Spring Data JPA equivalent:
     *     employeeRepository.findById(id);   ← Just 1 line!
     */
    public Employee getEmployee(Integer id) {
        Session session = factory.openSession();
        Transaction tx = null;
        Employee employee = null;

        try {
            tx = session.beginTransaction();
            employee = session.get(Employee.class, id);  // SELECT by primary key
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employee;
    }

    // ----------------------------------------------------------
    // UPDATE: Modify an Employee record
    // ----------------------------------------------------------

    /**
     * Hibernate UPDATE - Same boilerplate, again!
     *
     * Spring Data JPA equivalent:
     *     employeeRepository.save(employee);   ← Same 1 line! (save = insert or update)
     */
    public void updateEmployee(Employee employee) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(employee);             // UPDATE record
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ----------------------------------------------------------
    // DELETE: Remove an Employee record
    // ----------------------------------------------------------

    /**
     * Hibernate DELETE - Same boilerplate, again and again!
     *
     * Spring Data JPA equivalent:
     *     employeeRepository.deleteById(id);   ← Just 1 line!
     */
    public void deleteEmployee(Integer id) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.delete(employee);         // DELETE record
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

/*
 * ============================================================
 * SUMMARY — What's wrong with plain Hibernate?
 * ============================================================
 *
 *  PROBLEM                          LINES OF CODE
 *  ---------                        -------------
 *  addEmployee()                    15 lines  (but 1 line is actual logic)
 *  getEmployee()                    15 lines  (but 1 line is actual logic)
 *  updateEmployee()                 13 lines  (but 1 line is actual logic)
 *  deleteEmployee()                 16 lines  (but 2 lines are actual logic)
 *
 *  Total boilerplate: ~55 lines for 4 basic CRUD operations!
 *
 *  Spring Data JPA = 0 lines of boilerplate for the same 4 operations.
 * ============================================================
 */
