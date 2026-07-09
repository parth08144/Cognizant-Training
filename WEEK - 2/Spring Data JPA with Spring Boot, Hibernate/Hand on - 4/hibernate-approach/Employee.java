package com.cognizant.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Employee - JPA Entity used in both Hibernate and Spring Data JPA examples.
 *
 * The @Entity, @Table, @Id, @Column annotations come from JPA specification (javax.persistence).
 * Both Hibernate and Spring Data JPA read these annotations — this is what JPA standardizes!
 *
 * KEY POINT:
 *   The ENTITY class is identical whether you use plain Hibernate or Spring Data JPA.
 *   JPA standardizes how entities are DEFINED.
 *   What differs is how you QUERY and MANAGE those entities.
 */
@Entity
@Table(name = "employee")
public class Employee {

    /**
     * @Id             -> Primary key field
     * @GeneratedValue -> Auto-increment (DB generates the ID value)
     *                    IDENTITY = uses the DB column's AUTO_INCREMENT feature
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Integer id;

    @Column(name = "emp_first_name")
    private String firstName;

    @Column(name = "emp_last_name")
    private String lastName;

    @Column(name = "emp_salary")
    private double salary;

    // -------------------------------------------------------
    // Constructors
    // -------------------------------------------------------

    // No-arg constructor — Required by JPA spec
    public Employee() {}

    public Employee(String firstName, String lastName, double salary) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.salary    = salary;
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    // -------------------------------------------------------
    // toString()
    // -------------------------------------------------------

    @Override
    public String toString() {
        return "Employee{id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", salary=" + salary + '}';
    }
}
